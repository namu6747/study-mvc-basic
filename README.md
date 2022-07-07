# DBCP vs 기존 connection 속도 차이

```
Datebase connection pool lead time : 1
```

```
JDBC single lead time : 20
```


<hr/>

```
	Connection conn = null;
	try {
		DataSource ds = (DataSource)new InitialContext().lookup(
			"java:comp/env/jdbc/MySQLDBCP"
		);
		conn = ds.getConnection();
	} catch (Exception e) {
		System.out.println("connection 오류 : " + e.toString());
	}
```

```
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/bigdata";
	String username = "bigdata";
	String password = "12345";
	
	Connection conn = null;
	try{
		Class.forName(driver);
		conn = DriverManager.getConnection(
			url,username,password
		);
		out.print("커넥션 연결 완료");
	}catch(ClassNotFoundException e){
		out.println("Dirver class를 찾을 수 없습니다.");
	}catch(SQLException e){
		out.println("연결 요청 정보 오류 :"+e.getMessage());
	}
```

<br/>

### 꼭 DBCP를 사용하자
