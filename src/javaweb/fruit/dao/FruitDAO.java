package javaweb.fruit.dao;


import javaweb.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //获取指定页数的水果列表，每页显示5个
    List<Fruit> getFruitList(String keyword,Integer pageON);

    //获取水果信息
    Fruit getFruitById(Integer fid);

    //修改水果信息
    void updateFruit(Fruit fruit);

    //删除水果
    void delFruit(Integer fid);

    //添加水果
    void addFruit(Fruit fruit);

    //获取记录
    Integer getCount(String keyword);
}
