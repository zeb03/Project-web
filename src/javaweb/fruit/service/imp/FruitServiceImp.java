package javaweb.fruit.service.imp;

import javaweb.fruit.dao.FruitDAO;
import javaweb.fruit.pojo.Fruit;
import javaweb.fruit.service.FruitService;

import java.util.List;

/**
 * @author ze
 * @creat 2022-11-01-14:28
 */
public class FruitServiceImp implements FruitService {
    private FruitDAO fruitDAO = null;
    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageON) {
        return fruitDAO.getFruitList(keyword, pageON);
    }

    @Override
    public Fruit getFruitById(Integer fid) {
        return fruitDAO.getFruitById(fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public Integer getPageCount(String keyword) {
        Integer count = fruitDAO.getCount(keyword);
        return (count - 1) / 5 + 1;
    }
}
