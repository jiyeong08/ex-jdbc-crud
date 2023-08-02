package com.ohgiraffers.CRUD.delete;

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

public class deleteApp {

    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        menuDTO mdto = new menuDTO();

        Scanner sc = new Scanner(System.in);
        System.out.println("메뉴에서 삭제할 카테고리의 코드를 입력해주세요 : ");
        String categoryCode = sc.nextLine();

        mdto.setCategoryCode(categoryCode);

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menut-query.xml"));
            String query = prop.getProperty("deleteMENU");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, mdto.getCategoryCode());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        System.out.println("result : " + result);
    }
}
