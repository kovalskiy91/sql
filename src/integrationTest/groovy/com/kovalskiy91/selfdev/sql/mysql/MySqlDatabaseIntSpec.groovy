package com.kovalskiy91.selfdev.sql.mysql

import com.kovalskiy91.selfdev.sql.DatabaseIntSpec
import com.mysql.jdbc.Driver
import groovy.sql.Sql
import spock.lang.Ignore
import spock.lang.Shared

class MySqlDatabaseIntSpec extends DatabaseIntSpec {

    @Shared
    def sql = new Sql(dataSourceFrom(
            Driver.class,
            "jdbc:mysql://localhost:3306/db-sql",
            "admin",
            "SQL"
    ))

    @Ignore
    def "should connect when database is up"() {
        when:
        sql.execute("select 1")

        then:
        true
    }

    @Ignore
    def "should setup database"() {
        when:
        def setupSql = this.class.getResourceAsStream("/mysql-setup.sql").getText("UTF-8")
        def queries = setupSql.split(";")
        queries.each {
            def query = it
            if (query != null && !query.trim().empty) {
                println "Query: $query"
                sql.execute(query)
            }
        }

        then:
        true
    }


}
