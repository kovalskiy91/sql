package com.kovalskiy91.selfdev.sql.postgres

import com.kovalskiy91.selfdev.sql.DatabaseIntSpec
import groovy.sql.Sql
import org.postgresql.Driver
import spock.lang.Shared


class PostgresDatabaseIntSpec extends DatabaseIntSpec {

    @Shared
    def sql = new Sql(dataSourceFrom(
            Driver.class,
            "jdbc:postgresql://localhost:5432/db-sql",
            "admin",
            "SQL"
    ))

    def setup() {
        dropAllTables()
    }

    def dropAllTables() {
        sql.rows("select * from pg_tables pt where pt.schemaname = 'public'").forEach({ dropTable(it.tablename) })
    }

    def dropTable(table) {
        println("Drop table: $table")
        sql.execute("drop table $table cascade" as String)
    }

    void assertTableExists(table) {
        assert sql.rows("select * from pg_tables pt where pt.tablename = '$table'" as String).size() == 1
    }

    void assertTableDoesNotExist(table) {
        assert sql.rows("select * from pg_tables pt where pt.tablename = '$table'" as String).empty
    }


}
