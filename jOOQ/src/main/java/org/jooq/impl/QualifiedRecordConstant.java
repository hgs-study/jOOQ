/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: https://www.jooq.org/legal/licensing
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.jooq.impl;

import static org.jooq.conf.ParamType.INLINED;
import static org.jooq.impl.DSL.val;
import static org.jooq.impl.DefaultBinding.DefaultRecordBinding.REQUIRE_RECORD_CAST;
import static org.jooq.impl.Keywords.K_NULL;
import static org.jooq.impl.Keywords.K_ROW;
import static org.jooq.impl.Tools.getMappedUDTName;

import org.jooq.BindContext;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QualifiedRecord;
import org.jooq.RecordQualifier;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.QOM.UNotYetImplemented;

/**
 * @author Lukas Eder
 */
final class QualifiedRecordConstant<R extends QualifiedRecord<R>> extends AbstractParam<R> implements UNotYetImplemented {

    final RecordQualifier<R> qualifier;

    QualifiedRecordConstant(R value, RecordQualifier<R> qualifier) {
        super(value, qualifier.getDataType());

        this.qualifier = qualifier;
    }

    @Override
    public void accept(Context<?> ctx) {
        if (ctx instanceof RenderContext)
            toSQL0((RenderContext) ctx);
        else
            bind0((BindContext) ctx);
    }

    final void toSQL0(RenderContext ctx) {
        ParamType paramType = ctx.paramType();
        if (isInline())
            ctx.paramType(INLINED);

        switch (ctx.family()) {



































            // Due to lack of UDT support in the Postgres JDBC drivers, all UDT's
            // have to be inlined
            case POSTGRES:
            case YUGABYTEDB: {
                toSQLInline(ctx);
                break;
            }

            // Assume default behaviour if dialect is not available
            default:
                toSQLInline(ctx);
                break;
        }

        if (isInline())
            ctx.paramType(paramType);
    }

    private final void toSQLInline(RenderContext ctx) {
        Cast.renderCastIf(ctx,
            c -> {
                if (value == null) {
                    c.visit(K_NULL);
                }
                else {
                    switch (c.family()) {


                        case POSTGRES:
                        case YUGABYTEDB:
                            c.visit(K_ROW);
                            break;

                        default: {
                            c.visit(qualifier);
                            break;
                        }
                    }

                    c.sql('(');

                    String separator = "";
                    for (Field<?> field : value.fields()) {
                        c.sql(separator);
                        c.visit(val(value.get(field), field));
                        separator = ", ";
                    }

                    c.sql(')');
                }
            },

            // [#13174] Need to cast inline UDT ROW expressions to the UDT type
            c -> c.visit(qualifier),
            () -> REQUIRE_RECORD_CAST.contains(ctx.dialect())
        );
    }

    @Deprecated
    private final String getInlineConstructor(RenderContext ctx) {
        switch (ctx.family()) {


            case POSTGRES:
            case YUGABYTEDB:
                return "ROW";

            default:
                return getMappedUDTName(ctx, value);
        }
    }

    final void bind0(BindContext ctx) {
        switch (ctx.family()) {














            // Postgres cannot bind a complete structured type. The type is
            // inlined instead: ROW(.., .., ..)
            case POSTGRES:
            case YUGABYTEDB:  {
                if (value != null)
                    for (Field<?> field : value.fields())
                        ctx.visit(val(value.get(field), field.getDataType()));

                break;
            }

            default:
                throw new SQLDialectNotSupportedException("UDTs not supported in dialect " + ctx.dialect());
        }
    }
}
