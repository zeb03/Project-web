package javaweb.fruit.dao.impl;

import javaweb.fruit.dao.FruitDAO;
import javaweb.fruit.pojo.Fruit;
import javaweb.ssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList(String keyword,Integer pageOn) {
        return super.executeQuery("select * from t_fruit where fname like ? or remark like ? limit ?,5", "%"+keyword+"%","%"+keyword+"%",(pageOn - 1) * 5);
    }

    @Override
    public Fruit getFruitById(Integer fid) {
        return super.load("select * from t_fruit where fid = ?", fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ?, price = ?, fcount = ?, remark = ? where fid = ?";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public void delFruit(Integer fid) {
        String sql = "delete from t_fruit where fid = ?";
        super.executeUpdate(sql, fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit(fname,price,fcount,remark) values(?,?,?,?)";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }

    @Override
    public Integer getCount(String keyword) {
        String sql = "select count(*) from t_fruit where fname like ? or remark like ?";
        return ((Long)super.executeComplexQuery(sql,"%"+keyword+"%","%"+keyword+"%")[0]).intValue();
    }
}