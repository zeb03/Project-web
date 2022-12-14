package javaweb.fruit.dao.impl;

import javaweb.fruit.dao.FruitDAO;
import javaweb.fruit.exceptions.FruitDAOException;
import javaweb.fruit.pojo.Fruit;
import javaweb.ssm.basedao.BaseDAO;

import java.sql.SQLException;
import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList(String keyword,Integer pageOn) {
        try {
            return super.executeQuery("select * from t_fruit where fname like ? or remark like ? limit ?,5", "%"+keyword+"%","%"+keyword+"%",(pageOn - 1) * 5);
        } catch (Exception e){
            throw new FruitDAOException("FruitDAO have problems");
        }
    }

    @Override
    public Fruit getFruitById(Integer fid) {
        try {
            return super.load("select * from t_fruit where fid = ?", fid);
        } catch (Exception e){
            throw new FruitDAOException("FruitDAO have problems");
        }
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ?, price = ?, fcount = ?, remark = ? where fid = ?";
        try {
            super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
        } catch (SQLException e) {
            throw new FruitDAOException("FruitDAO have problems");
        }
    }

    @Override
    public void delFruit(Integer fid) {
        String sql = "delete from t_fruit where fid = ?";
        try {
            super.executeUpdate(sql, fid);
        } catch (SQLException e) {
            throw new FruitDAOException("FruitDAO have problems");
        }
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit(fname,price,fcount,remark) values(?,?,?,?)";
        try {
            super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
        } catch (SQLException e) {
            throw new FruitDAOException("FruitDAO have problems");
        }
    }

    @Override
    public Integer getCount(String keyword) {
        try {
            String sql = "select count(*) from t_fruit where fname like ? or remark like ?";
            return ((Long)super.executeComplexQuery(sql,"%"+keyword+"%","%"+keyword+"%")[0]).intValue();
        } catch (SQLException e) {
            throw new FruitDAOException("FruitDAO have problems");
        }
    }
}