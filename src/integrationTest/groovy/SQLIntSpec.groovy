import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class SQLIntSpec extends Specification {

    @Shared
    Sql sql = new Sql(dataSource())

    def dataSource() {
        def dataSource = new HikariDataSource()
        dataSource.jdbcUrl = "jdbc:postgresql://localhost:5432/db-sql"
        dataSource.username = "admin"
        dataSource.password = "SQL"
        dataSource
    }

    def setup() {
        dropAllTables()
    }

    def dropAllTables() {
        sql.rows("select * from pg_tables pt where pt.schemaname = 'public'").forEach({ dropTable(it.tablename) })
    }

    def dropTable(table) {
        println("Drop table: $table")
        sql.execute("drop table $table" as String)
    }

    void assertTableExists(table) {
        assert sql.rows("select * from pg_tables pt where pt.tablename = '$table'" as String).size() == 1
    }

}
