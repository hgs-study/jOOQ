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
package org.jooq;

import org.jetbrains.annotations.*;


import java.util.Collection;

import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

/**
 * This type is used for the {@link Select}'s DSL API when selecting generic
 * {@link Record} types.
 * <p>
 * Example: <pre><code>
 * -- get all authors' first and last names, and the number
 * -- of books they've written in German, if they have written
 * -- more than five books in German in the last three years
 * -- (from 2011), and sort those authors by last names
 * -- limiting results to the second and third row
 *
 *   SELECT T_AUTHOR.FIRST_NAME, T_AUTHOR.LAST_NAME, COUNT(*)
 *     FROM T_AUTHOR
 *     JOIN T_BOOK ON T_AUTHOR.ID = T_BOOK.AUTHOR_ID
 *    WHERE T_BOOK.LANGUAGE = 'DE'
 *      AND T_BOOK.PUBLISHED &gt; '2008-01-01'
 * GROUP BY T_AUTHOR.FIRST_NAME, T_AUTHOR.LAST_NAME
 *   HAVING COUNT(*) &gt; 5
 * ORDER BY T_AUTHOR.LAST_NAME ASC NULLS FIRST
 *    LIMIT 2
 *   OFFSET 1
 *      FOR UPDATE
 *       OF FIRST_NAME, LAST_NAME
 *       NO WAIT
 * </code></pre> Its equivalent in jOOQ <pre><code>
 * create.select(TAuthor.FIRST_NAME, TAuthor.LAST_NAME, create.count())
 *       .from(T_AUTHOR)
 *       .join(T_BOOK).on(TBook.AUTHOR_ID.equal(TAuthor.ID))
 *       .where(TBook.LANGUAGE.equal("DE"))
 *       .and(TBook.PUBLISHED.greaterThan(parseDate('2008-01-01')))
 *       .groupBy(TAuthor.FIRST_NAME, TAuthor.LAST_NAME)
 *       .having(create.count().greaterThan(5))
 *       .orderBy(TAuthor.LAST_NAME.asc().nullsFirst())
 *       .limit(2)
 *       .offset(1)
 *       .forUpdate()
 *       .of(TAuthor.FIRST_NAME, TAuthor.LAST_NAME)
 *       .noWait();
 * </code></pre> Refer to the manual for more details
 * <p>
 * <h3>Referencing <code>XYZ*Step</code> types directly from client code</h3>
 * <p>
 * It is usually not recommended to reference any <code>XYZ*Step</code> types
 * directly from client code, or assign them to local variables. When writing
 * dynamic SQL, creating a statement's components dynamically, and passing them
 * to the DSL API statically is usually a better choice. See the manual's
 * section about dynamic SQL for details: <a href=
 * "https://www.jooq.org/doc/latest/manual/sql-building/dynamic-sql">https://www.jooq.org/doc/latest/manual/sql-building/dynamic-sql</a>.
 * <p>
 * Drawbacks of referencing the <code>XYZ*Step</code> types directly:
 * <ul>
 * <li>They're operating on mutable implementations (as of jOOQ 3.x)</li>
 * <li>They're less composable and not easy to get right when dynamic SQL gets
 * complex</li>
 * <li>They're less readable</li>
 * <li>They might have binary incompatible changes between minor releases</li>
 * </ul>
 *
 * @author Lukas Eder
 */
public interface SelectOnStep<R extends Record> {

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>,
     * connecting them with each other with {@link Operator#AND}.
     */
    @NotNull @CheckReturnValue
    @Support
    SelectOnConditionStep<R> on(Condition condition);

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>,
     * connecting them with each other with {@link Operator#AND}.
     */
    @NotNull @CheckReturnValue
    @Support
    SelectOnConditionStep<R> on(Condition... conditions);

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>.
     */
    @NotNull @CheckReturnValue
    @Support
    SelectOnConditionStep<R> on(Field<Boolean> condition);

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(SQL)
     * @see SQL
     */
    @NotNull @CheckReturnValue
    @Support
    @PlainSQL
    SelectOnConditionStep<R> on(SQL sql);

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String)
     * @see SQL
     */
    @NotNull @CheckReturnValue
    @Support
    @PlainSQL
    SelectOnConditionStep<R> on(String sql);

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, Object...)
     * @see DSL#sql(String, Object...)
     * @see SQL
     */
    @NotNull @CheckReturnValue
    @Support
    @PlainSQL
    SelectOnConditionStep<R> on(String sql, Object... bindings);

    /**
     * Add an <code>ON</code> clause to the previous <code>JOIN</code>.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, QueryPart...)
     * @see DSL#sql(String, QueryPart...)
     * @see SQL
     */
    @NotNull @CheckReturnValue
    @Support
    @PlainSQL
    SelectOnConditionStep<R> on(String sql, QueryPart... parts);

    /**
     * Join the previous table on a non-ambiguous foreign key relationship
     * between the two joined tables.
     * <p>
     * See {@link TableOnStep#onKey(ForeignKey)} for examples.
     *
     * @see TableOnStep#onKey(ForeignKey)
     * @throws DataAccessException If there is no non-ambiguous key definition
     *             known to jOOQ
     */
    @NotNull @CheckReturnValue
    @Support
    SelectOnConditionStep<R> onKey() throws DataAccessException;

    /**
     * Join the previous table on a non-ambiguous foreign key relationship
     * between the two joined tables.
     * <p>
     * See {@link TableOnStep#onKey(ForeignKey)} for examples.
     *
     * @see TableOnStep#onKey(ForeignKey)
     * @throws DataAccessException If there is no non-ambiguous key definition
     *             known to jOOQ
     */
    @NotNull @CheckReturnValue
    @Support
    SelectOnConditionStep<R> onKey(TableField<?, ?>... keyFields) throws DataAccessException;

    /**
     * Join the table on a non-ambiguous foreign key relationship between the
     * two joined tables.
     * <p>
     * See {@link TableOnStep#onKey(ForeignKey)} for examples.
     *
     * @see TableOnStep#onKey(ForeignKey)
     */
    @NotNull @CheckReturnValue
    @Support
    SelectOnConditionStep<R> onKey(ForeignKey<?, ?> key);

    /**
     * Join the previous table with the <code>USING(column [, column…])</code>
     * syntax.
     * <p>
     * If this is not supported by your RDBMS, then jOOQ will try to emulate
     * this behaviour using the information provided in this query.
     */
    @NotNull @CheckReturnValue
    @Support
    SelectJoinStep<R> using(Field<?>... fields);

    /**
     * Join the previous table with the <code>USING(column [, column…])</code>
     * syntax.
     * <p>
     * If this is not supported by your RDBMS, then jOOQ will try to emulate
     * this behaviour using the information provided in this query.
     */
    @NotNull @CheckReturnValue
    @Support
    SelectJoinStep<R> using(Collection<? extends Field<?>> fields);
}
