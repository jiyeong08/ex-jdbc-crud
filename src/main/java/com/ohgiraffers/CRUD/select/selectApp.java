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

        List<menuDTO> menulist = new ArrayList<menuDTO>();
        menuDTO mdto = new menuDTO();

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 메뉴의 카테고리코드를 입력해주세요 : ");
        int categoryCode = sc.nextInt();

        mdto.setCategoryCode(categoryCode);

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menut-query.xml"));
            String query = prop.getProperty("selectMENU");

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, mdto.getCategoryCode());
            rset = pstmt.executeQuery();

            while(rset.next()){
                int menuCode = rset.getInt("menu_code");
                String menuName = rset.getString("menu_name");
                int menuPrice = rset.getInt("menu_price");
                String orderableStatus = rset.getString("orderable_status");

                menulist.add(mdto);

                System.out.println(menuName + "의 가격은 " + menuPrice + "원이며, 메뉴코드는 " + menuCode + "이고, 주문가능상태는" + orderableStatus + "입니다.");

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
