package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.OrderDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {

    public static boolean updateOrder(OrderDto orderDto) throws SQLException {
        return CrudUtil.execute(
                "update Orders set Customer_ID = ?, Order_Date = ?, Order_Time = ?, Pots_Size = ?, Quantity = ? where Order_ID = ?",
                orderDto.getCustomerId(),
                orderDto.getDate(),
                orderDto.getTime(),
                orderDto.getPotsSize(),
                orderDto.getQuantity(),
                orderDto.getOrderId()
        );
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Order_ID from Orders order by Order_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

    public ArrayList<OrderDto> viewAllOrder() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders");
        ArrayList<OrderDto> orders = new ArrayList<>();
        while (rs.next()) {
            OrderDto orderDto = new OrderDto(
                    rs.getInt("Order_ID"),
                    rs.getInt("Customer_ID"),
                    rs.getString("Order_Date"),
                    rs.getTime("Order_Time"),
                    rs.getInt("Pots_Size"),
                    rs.getInt("Quantity")
            );
            orders.add(orderDto);
        }
        return orders;
    }
    public boolean saveOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Orders values (?,?,?,?,?,?)",
                orderDto.getOrderId(),
                orderDto.getCustomerId(),
                orderDto.getDate(),
                orderDto.getTime(),
                orderDto.getPotsSize(),
                orderDto.getQuantity()
        );
    }

    public boolean deleteOrder(OrderDto orderDto) throws SQLException{
        String sql = "delete from Orders where Order_ID=?";
        return CrudUtil.execute(sql,orderDto.getOrderId());
    }
}
