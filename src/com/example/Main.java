package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) {
		Connection con = null;
		try {
			//SQLite JDBC 체크
			Class.forName("org.sqlite.JDBC");
			
			//데이터베이스 파일 연결 
			String dbFile = "myfirst.db";
			con = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
			
			//메뉴
			Scanner sc = new Scanner(System.in);
			String name ="", a_type="", a_year="", debut="";
			int menu=0;
			
			while(menu!=5) {
				System.out.println("Select menu");
				System.out.print("1. 조회 \n2. 추가 \n3. 수정 \n4. 삭제\n5. 종료\n-> ");
				menu = sc.nextInt();
				if(menu==1) {
					//데이터 조회
					System.out.println("\n*** 데이터 조회 ***");
					Statement stat1 = con.createStatement();
					String sql1 = "select * from g_artists";
					ResultSet rs1 = stat1.executeQuery(sql1);
					while(rs1.next()) {
						String id = rs1.getString("id");
						name = rs1.getString("name");
						a_type = rs1.getString("a_type");
						a_year = rs1.getString("a_year");
						debut = rs1.getString("debut");
						String regdate = rs1.getString("regdate");
						System.out.println(id + " " + name + " " + a_type +" " 
								+ a_year + " " + debut + " " + regdate);
					}
					stat1.close();
					System.out.print("\n");
				}
				
				else if(menu==2) {
					//데이터 추가
					System.out.println("\n*** 새 데이터 추가 ***");
					Statement stat2 = con.createStatement();
					String sql2 = "insert into g_artists (name, a_type, a_year, debut, regdate) ";
					
					System.out.println("name? ");
					name = sc.next(); 
					System.out.println("a_type? ");
					a_type = sc.next();
					System.out.println("a_year? ");
					a_year = sc.next();
					System.out.println("debut? ");
					debut = sc.next();
					sql2 = sql2 + "values ('" + name + "', '" + a_type + "', '" 
							+ a_year + "', '" + debut + "', datetime('now','localtime'));";
					
					int cnt = stat2.executeUpdate(sql2);
					if(cnt>0) 
						System.out.println("데이터가 추가되었습니다.");
					else 
						System.out.println("[Error] 데이터 추가 오류!");
					stat2.close();
					System.out.print("\n");
				}
				
				else if(menu==3) {
					//데이터 수정
					System.out.println("\n*** 데이터 수정 ***");
					Statement stat3 = con.createStatement();
					String sql3 = "update g_artists set ";
			
					System.out.println("수정할 id 번호? ");
					String id = sc.next();
					sc.nextLine();
					System.out.print("변수명= '변경값' 입력 -> ");
					String updateData = sc.nextLine();
					sql3 = sql3 + updateData + " where id=" + id + ";";
					
					int cnt4 = stat3.executeUpdate(sql3);
					if(cnt4>0) 
						System.out.println("데이터가 수정되었습니다.");
					else
						System.out.println("[Error] 데이터 수정 오류");
					stat3.close();
					System.out.print("\n");
				}
				
				else if(menu==4) {
					//데이터 삭제
					System.out.println("\n*** 데이터 삭제 ***");
					Statement stat4 = con.createStatement();
					String sql4 = "delete from g_artists where id=";
					
					System.out.println("id? ");
					String id = sc.next();
					sql4 = sql4 + id + ";";
					
					int cnt3 = stat4.executeUpdate(sql4);
					if(cnt3>0) 
						System.out.println("데이터가 삭제되었습니다!");
					else
						System.out.println("[Error] 데이터 삭제 오류");
					stat4.close();
					System.out.print("\n");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {}
			}
		}
	}

}
