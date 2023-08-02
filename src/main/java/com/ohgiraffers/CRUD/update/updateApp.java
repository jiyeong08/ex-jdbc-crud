package com.ohgiraffers.CRUD.update;

import com.ohgiraffers.model.dto.menuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class updateApp {

    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        menuDTO mdto = new menuDTO();

        Scanner sc = new Scanner(System.in);
        System.out.println("변경된 가격을 입력해주세요 : ");
        String menuPrice = sc.nextLine();
        System.out.println("변경된 주문가능상태를 입력해주세요 : ");
        String orderableStatus = sc.nextLine();
        System.out.println("변경된 카테고리코드를 입력해주세요 : ");
        String categoryCode = sc.nextLine();
        System.out.println("변경하실 메뉴의 이름을 확인해주세요 : ");
        String menuName = sc.nextLine();

        mdto.setMenuPrice(menuPrice);
        mdto.setOrderableStatus(orderableStatus);
        mdto.setCategoryCode(categoryCode);
        mdto.setMenuName(menuName);

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menut-query.xml"));
            String query = prop.getProperty("updateMENU");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, mdto.getMenuPrice());
            pstmt.setString(2, mdto.getOrderableStatus());
            pstmt.setString(3, mdto.getCategoryCode());
            pstmt.setString(4, mdto.getMenuName());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }
        System.out.println("result : " + result);
    }
}
