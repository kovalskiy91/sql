class TableIntSpec extends SQLIntSpec {

    def "should create table"() {
        given:
        def table = "person"
        sql.execute("create table $table (first_name varchar, last_name varchar, birth_date timestamp)" as String)

        expect:
        assertTableExists(table)
    }

}
