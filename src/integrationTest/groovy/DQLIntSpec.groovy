class DQLIntSpec extends SQLIntSpec {

    def setup() {
        sql.execute("create table department (" +
                "dept_id serial not null," +
                "name text not null," +
                "constraint pk_department primary key (dept_id)" +
                ");")
        sql.execute("create table branch (" +
                "branch_id serial not null," +
                "name text not null," +
                "address text," +
                "city text," +
                "state text," +
                "zip text," +
                "constraint pk_branch primary key (branch_id)" +
                ");")
        sql.execute("create table employee (" +
                "emp_id serial not null," +
                "fname text not null," +
                "lname text not null," +
                "start_date timestamp not null," +
                "end_date timestamp," +
                "superior_emp_id integer," +
                "dept_id integer," +
                "title text," +
                "assigned_branch_id integer," +
                "constraint fk_e_emp_id foreign key (superior_emp_id) references employee (emp_id)," +
                "constraint fk_dept_id foreign key (dept_id) references department (dept_id)," +
                "constraint fk_e_branch_id foreign key (assigned_branch_id) references branch (branch_id)," +
                "constraint pk_employee primary key (emp_id)" +
                ");")
        sql.execute("create table product_type (" +
                "product_type_cd text not null," +
                "name text not null," +
                "constraint pk_product_type primary key (product_type_cd)" +
                ");")
        sql.execute("create table product (" +
                "product_cd text not null," +
                "name text not null," +
                "product_type_cd text not null," +
                "date_offered date," +
                "date_retired date," +
                "constraint fk_product_type_cd foreign key (product_type_cd) references product_type (product_type_cd)," +
                "constraint pk_product primary key (product_cd)" +
                ");")
        sql.execute("create table customer (" +
                "cust_id serial not null," +
                "fed_id text not null," +
                "cust_type_cd text not null," +
                "address text," +
                "city text," +
                "state text," +
                "postal_code text," +
                "constraint pk_customer primary key (cust_id)" +
                ");")
        sql.execute("create table individual (" +
                "cust_id serial not null," +
                "fname text not null," +
                "lname text not null," +
                "birth_date timestamp," +
                "constraint fk_i_cust_id foreign key (cust_id) references customer (cust_id)," +
                "constraint pk_individual primary key (cust_id)" +
                ");")
        sql.execute("create table business (" +
                "cust_id serial not null," +
                "name text not null," +
                "state_id text not null," +
                "incorp_date date," +
                "constraint fk_b_cust_id foreign key (cust_id) references customer (cust_id)," +
                "constraint pk_business primary key (cust_id)" +
                " );")
        sql.execute("create table officer (" +
                "officer_id serial not null," +
                "cust_id integer not null," +
                "fname text not null," +
                "lname text not null," +
                "title text," +
                "start_date timestamp not null," +
                "end_date timestamp," +
                "constraint fk_o_cust_id foreign key (cust_id) references business (cust_id)," +
                "constraint pk_officer primary key (officer_id)" +
                ");")
        sql.execute("create table account (" +
                "account_id serial not null," +
                "product_cd text not null," +
                "cust_id integer not null," +
                "open_date timestamp not null," +
                "close_date timestamp," +
                "last_activity_date timestamp," +
                "status text," +
                "open_branch_id integer," +
                "open_emp_id integer," +
                "avail_balance numeric(10,2)," +
                "pending_balance numeric(10,2)," +
                "constraint fk_product_cd foreign key (product_cd) references product (product_cd)," +
                "constraint fk_a_cust_id foreign key (cust_id) references customer (cust_id)," +
                "constraint fk_a_branch_id foreign key (open_branch_id) references branch (branch_id)," +
                "constraint fk_a_emp_id foreign key (open_emp_id) references employee (emp_id)," +
                "constraint pk_account primary key (account_id)" +
                ");")
        sql.execute("create table transaction (" +
                "txn_id serial not null," +
                "txn_date timestamp not null," +
                "account_id integer not null," +
                "txn_type_cd text," +
                "amount numeric(10,2) not null," +
                "teller_emp_id integer," +
                "execution_branch_id integer," +
                "funds_avail_date timestamp," +
                "constraint fk_t_account_id foreign key (account_id) references account (account_id)," +
                "constraint fk_teller_emp_id foreign key (teller_emp_id) references employee (emp_id)," +
                "constraint fk_exec_branch_id foreign key (execution_branch_id) references branch (branch_id)," +
                "constraint pk_transaction primary key (txn_id)" +
                ");")

        /* department data */
        sql.execute("insert into department(name) values('Operations');")
        sql.execute("insert into department(name) values('Loans');")
        sql.execute("insert into department(name) values('Administration');")

        /* branch data */
        sql.execute("insert into branch(name, address, city, state, zip) values('Headquarters', '3882 Main St.', 'Waltham', 'MA', '02451');")
        sql.execute("insert into branch(name, address, city, state, zip) values('Woburn Branch', '422 Maple St.', 'Woburn', 'MA', '01801');")
        sql.execute("insert into branch(name, address, city, state, zip) values('Quincy Branch', '125 Presidential Way', 'Quincy', 'MA', '02169');")
        sql.execute("insert into branch(name, address, city, state, zip) values('So. NH Branch', '378 Maynard Ln.', 'Salem', 'NH', '03079');")

        /* employee data */
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Michael', 'Smith', '2001-06-22', (select dept_id from department where name = 'Administration'), 'President', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Susan', 'Barker', '2002-09-12', (select dept_id from department where name = 'Administration'), 'Vice President', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Robert', 'Tyler', '2000-02-09', (select dept_id from department where name = 'Administration'), 'Treasurer', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Susan', 'Hawthorne', '2002-04-24', (select dept_id from department where name = 'Operations'), 'Operations Manager', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('John', 'Gooding', '2003-11-14', (select dept_id from department where name = 'Loans'), 'Loan Manager', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Helen', 'Fleming', '2004-03-17', (select dept_id from department where name = 'Operations'), 'Head Teller', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Chris', 'Tucker', '2004-09-15', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Sarah', 'Parker', '2002-12-02', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Jane', 'Grossman', '2002-05-03', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Headquarters'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Paula', 'Roberts', '2002-07-27', (select dept_id from department where name = 'Operations'), 'Head Teller', (select branch_id from branch where name = 'Woburn Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Thomas', 'Ziegler', '2000-10-23', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Woburn Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Samantha', 'Jameson', '2003-01-08', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Woburn Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('John', 'Blake', '2000-05-11', (select dept_id from department where name = 'Operations'), 'Head Teller', (select branch_id from branch where name = 'Quincy Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Cindy', 'Mason', '2002-08-09', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Quincy Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Frank', 'Portman', '2003-04-01', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'Quincy Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Theresa', 'Markham', '2001-03-15', (select dept_id from department where name = 'Operations'), 'Head Teller', (select branch_id from branch where name = 'So. NH Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Beth', 'Fowler', '2002-06-29', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'So. NH Branch'));")
        sql.execute("insert into employee (fname, lname, start_date, dept_id, title, assigned_branch_id) values('Rick', 'Tulman', '2002-12-12', (select dept_id from department where name = 'Operations'), 'Teller', (select branch_id from branch where name = 'So. NH Branch'));")


    }

    def "should select database version"() {
        expect:
        def version = sql.firstRow("select version()").version as String
        version.startsWith("PostgreSQL 11.1")
    }

    def "should select all employees"() {
        expect:
        sql.rows("select * from employee").size() == 18
    }

    def "should select names of all departments"() {
        when:
        def rows = sql.rows("select name from department")

        then:
        rows.collect({ it.name }).toSet() == ['Operations', 'Loans', 'Administration'].toSet()
    }

    def "should select expressions from employee"() {
        when:
        def rows = sql.rows("select 'ACTIVE' as state, id * 10 as idx10, id, upper(fname) as upper_fname, fname from employee")

        then:
        rows.each {
            println(it)
            assert it.getAt("state") == 'ACTIVE'
            assert it.getAt("idx10") == it.getAt("id") * 10
            assert it.getAt("upper_fname") == it.getAt("fname").toString().toUpperCase()
        }
    }

    def "should select from temporal query"() {
        expect:
        def rows = sql.rows("select te.employee_id, te.fname from (" +
                "select e.id as employee_id, e.fname from employee e" +
                ") te")
        rows.each { println(it) }
        rows.size() == 18
    }

    def "should select number of employees having same name"() {
        expect:
        def rows = sql.rows("select count(e.id), e.fname, e.lname from employee e group by e.fname")
        println(rows)
        rows.size() == 16
    }

    def "should select when there are more than 2 employees having same name"() {
        expect:
        def rows = sql.rows("select count(e.id), e.fname from employee e group by e.fname having count(e.id) > 1")
        println(rows)
        rows.each {
            assert it.count > 1
        }
    }


}
