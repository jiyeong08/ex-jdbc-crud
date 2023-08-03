package com.ohgiraffers.CRUD.select;

import com.ohgiraffers.model.dto.menuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class selectApp {

    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 메뉴의 카테고리코드를 입력해주세요 : ");
        String categoryCode = sc.nextLine();

            List<menuDTO> menulist = new ArrayList<>();



        Properties prop = new Properties();

            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menut-query.xml"));
            String query = prop.getProperty("selectMENU");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, categoryCode);
            rset = pstmt.executeQuery();

            while(rset.next()){
                menuDTO mdto = new menuDTO();

                mdto.setMenuCode(rset.getString("menu_code"));
                mdto.setMenuName(rset.getString("menu_name"));
                mdto.setMenuPrice(rset.getString("menu_price"));
                mdto.setOrderableStatus(rset.getString("orderable_status"));

                menulist.add(mdto);
            }
            for(menuDTO menu : menulist){
                System.out.printf(menu.getMenuName() + "의 가격은 ");
                System.out.printf(menu.getMenuPrice() + "원이며, 메뉴코드는 ");
                System.out.printf(menu.getMenuCode() + "이고, 주문가능상태는 ");
                System.out.println(menu.getOrderableStatus() + "입니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }

    }
}
