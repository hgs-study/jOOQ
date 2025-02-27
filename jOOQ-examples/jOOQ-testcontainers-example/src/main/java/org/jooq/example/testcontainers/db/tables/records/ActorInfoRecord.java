/*
 * This file is generated by jOOQ.
 */
package org.jooq.example.testcontainers.db.tables.records;


import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.example.testcontainers.db.tables.ActorInfo;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActorInfoRecord extends TableRecordImpl<ActorInfoRecord> implements Record4<Long, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.actor_info.actor_id</code>.
     */
    public void setActorId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.actor_info.actor_id</code>.
     */
    public Long getActorId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.actor_info.first_name</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.actor_info.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.actor_info.last_name</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.actor_info.last_name</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.actor_info.film_info</code>.
     */
    public void setFilmInfo(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.actor_info.film_info</code>.
     */
    public String getFilmInfo() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return ActorInfo.ACTOR_INFO.ACTOR_ID;
    }

    @Override
    public Field<String> field2() {
        return ActorInfo.ACTOR_INFO.FIRST_NAME;
    }

    @Override
    public Field<String> field3() {
        return ActorInfo.ACTOR_INFO.LAST_NAME;
    }

    @Override
    public Field<String> field4() {
        return ActorInfo.ACTOR_INFO.FILM_INFO;
    }

    @Override
    public Long component1() {
        return getActorId();
    }

    @Override
    public String component2() {
        return getFirstName();
    }

    @Override
    public String component3() {
        return getLastName();
    }

    @Override
    public String component4() {
        return getFilmInfo();
    }

    @Override
    public Long value1() {
        return getActorId();
    }

    @Override
    public String value2() {
        return getFirstName();
    }

    @Override
    public String value3() {
        return getLastName();
    }

    @Override
    public String value4() {
        return getFilmInfo();
    }

    @Override
    public ActorInfoRecord value1(Long value) {
        setActorId(value);
        return this;
    }

    @Override
    public ActorInfoRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public ActorInfoRecord value3(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public ActorInfoRecord value4(String value) {
        setFilmInfo(value);
        return this;
    }

    @Override
    public ActorInfoRecord values(Long value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActorInfoRecord
     */
    public ActorInfoRecord() {
        super(ActorInfo.ACTOR_INFO);
    }

    /**
     * Create a detached, initialised ActorInfoRecord
     */
    public ActorInfoRecord(Long actorId, String firstName, String lastName, String filmInfo) {
        super(ActorInfo.ACTOR_INFO);

        setActorId(actorId);
        setFirstName(firstName);
        setLastName(lastName);
        setFilmInfo(filmInfo);
    }
}
