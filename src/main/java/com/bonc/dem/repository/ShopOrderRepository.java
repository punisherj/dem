package com.bonc.dem.repository;

import com.bonc.dem.entity.ShopOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrderEntity, BigInteger> {

    @Query(value =
            "SELECT t.owner , count(*) " +
                    "FROM oc_shop_order t " +
                    "WHERE DATE_FORMAT(t.prov_order_time , '%Y%m%d') = ?1 AND t.cb_order_status_code = ?2 AND t.owner IS NOT NULL " +
                    "GROUP BY DATE_FORMAT(t.create_date , '%Y%m%d'), t.owner",
            nativeQuery = true)
    List<Object[]> kaihuSucess(String time, Integer code);

    @Query(value =
            " SELECT SUM(aaa.cc), aaa.owner, dd FROM ( " +
                    " SELECT " +
                        " a.owner, " +
                        " MAX(a.cb_order_status_code) , " +
                        " COUNT(*) cc ," +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') dd " +
                    " FROM oc_shop_order a WHERE " +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') = ?1 " +
                        " AND a.cb_order_status_code <> ?2" +
                    " GROUP BY" +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') ," +
                        " a.owner" +
                        " UNION ALL " +
                        " SELECT " +
                        " a.owner ," +
                        " MAX(a.cb_order_status_code) ," +
                        " COUNT(*) cc ," +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') " +
                    " FROM oc_shop_order_bak a WHERE " +
                        "DATE_FORMAT(a.prov_order_time , '%Y%m%d') = ?1 " +
                        "AND a.cb_order_status_code = '-1'" +
                    " GROUP BY " +
                        " DATE_FORMAT(a.prov_order_time , '%Y%m%d') , " +
                        " a.owner " +
                    " ) aaa " +
                    " GROUP BY " +
                    " aaa.owner, " +
                    " dd " +
                    " ORDER BY " +
                    "dd",
            nativeQuery = true)
    List<Object[]> kaihuFail(String time, Integer code);
}
