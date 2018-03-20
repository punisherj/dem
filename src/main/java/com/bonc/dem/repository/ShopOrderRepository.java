package com.bonc.dem.repository;

import com.bonc.dem.entity.ShopOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrderEntity, BigInteger> {

    @Query(value =
            "SELECT t.area_name , count(*) " +
                    "FROM oc_shop_order t " +
                    "WHERE DATE_FORMAT(t.prov_order_time , '%Y%m%d') = ?1 AND t.cb_order_status_code = ?2 " +
                    "GROUP BY DATE_FORMAT(t.create_date , '%Y%m%d'), t.area_name",
            nativeQuery = true)
    List<Object[]> createAccountSuccess(String time, Integer code);

    @Query(value =
            " SELECT SUM(aaa.cc), aaa.area_name, dd FROM ( " +
                    " SELECT " +
                        " a.area_name, " +
                        " MAX(a.cb_order_status_code) , " +
                        " COUNT(*) cc ," +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') dd " +
                    " FROM oc_shop_order a WHERE " +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') = ?1 " +
                        " AND a.cb_order_status_code <> ?2" +
                    " GROUP BY" +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') ," +
                        " a.area_name" +
                        " UNION ALL " +
                        " SELECT " +
                        " a.area_name ," +
                        " MAX(a.cb_order_status_code) ," +
                        " COUNT(*) cc ," +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') " +
                    " FROM oc_shop_order_bak a WHERE " +
                        "DATE_FORMAT(a.prov_order_time , '%Y%m%d') = ?1 " +
                        "AND a.cb_order_status_code = '-1'" +
                    " GROUP BY " +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') , " +
                        " a.area_name " +
                    " ) aaa " +
                    " GROUP BY " +
                    " aaa.area_name, " +
                    " dd " +
                    " ORDER BY " +
                    "dd",
            nativeQuery = true)
    List<Object[]> createAccountFail(String time, Integer code);
}
