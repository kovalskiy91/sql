package com.kovalskiy91.selfdev.sql.postgres

class DDLIntSpec extends PostgresDatabaseIntSpec {

    def "should create table"() {
        given:
        def personTable = "person"
        def identityCardTable = "identity_card"
        sql.execute("create table $personTable (" +
                "id integer, " +
                "first_name varchar, " +
                "last_name varchar, " +
                "birth_date timestamp, " +
                "constraint pk_person primary key (id)" +
                ")" as String)
        sql.execute("create table $identityCardTable (" +
                "id integer, " +
                "person_id integer, " +
                "issue_date timestamp, " +
                "constraint pk_identity_card primary key (id), " +
                "constraint fk_identity_card_person foreign key (person_id) references person(id)" +
                ")" as String)

        expect:
        assertTableExists(personTable)
        assertTableExists(identityCardTable)
    }

    def "should drop table"() {
        given:
        def toDropTable = "to_drop"
        sql.execute("create table $toDropTable (" +
                "id integer primary key, " +
                "some_column varchar" +
                ")" as String)
        assertTableExists(toDropTable)

        when:
        sql.execute("drop table $toDropTable" as String)

        then:
        assertTableDoesNotExist(toDropTable)
    }

}
