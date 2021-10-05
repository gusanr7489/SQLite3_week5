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
			//SQLite JDBC üũ
			Class.forName("org.sqlite.JDBC");
			
			//�����ͺ��̽� ���� ���� 
			String dbFile = "myfirst.db";
			con = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
			
			//�޴�
			Scanner sc = new Scanner(System.in);
			String name ="", a_type="", a_year="", debut="";
			int menu=0;
			
			while(menu!=5) {
				System.out.println("Select menu");
				System.out.print("1. ��ȸ \n2. �߰� \n3. ���� \n4. ����\n5. ����\n-> ");
				menu = sc.nextInt();
				if(menu==1) {
					//������ ��ȸ
					System.out.println("\n*** ������ ��ȸ ***");
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
					//������ �߰�
					System.out.println("\n*** �� ������ �߰� ***");
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
						System.out.println("�����Ͱ� �߰��Ǿ����ϴ�.");
					else 
						System.out.println("[Error] ������ �߰� ����!");
					stat2.close();
					System.out.print("\n");
				}
				
				else if(menu==3) {
					//������ ����
					System.out.println("\n*** ������ ���� ***");
					Statement stat3 = con.createStatement();
					String sql3 = "update g_artists set ";
			
					System.out.println("������ id ��ȣ? ");
					String id = sc.next();
					sc.nextLine();
					System.out.print("������= '���氪' �Է� -> ");
					String updateData = sc.nextLine();
					sql3 = sql3 + updateData + " where id=" + id + ";";
					
					int cnt4 = stat3.executeUpdate(sql3);
					if(cnt4>0) 
						System.out.println("�����Ͱ� �����Ǿ����ϴ�.");
					else
						System.out.println("[Error] ������ ���� ����");
					stat3.close();
					System.out.print("\n");
				}
				
				else if(menu==4) {
					//������ ����
					System.out.println("\n*** ������ ���� ***");
					Statement stat4 = con.createStatement();
					String sql4 = "delete from g_artists where id=";
					
					System.out.println("id? ");
					String id = sc.next();
					sql4 = sql4 + id + ";";
					
					int cnt3 = stat4.executeUpdate(sql4);
					if(cnt3>0) 
						System.out.println("�����Ͱ� �����Ǿ����ϴ�!");
					else
						System.out.println("[Error] ������ ���� ����");
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
