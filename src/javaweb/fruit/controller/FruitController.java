package javaweb.fruit.controller;

import javaweb.fruit.pojo.Fruit;
import javaweb.fruit.service.FruitService;
import javaweb.ssm.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ze
 * @creat 2022-10-29-22:55
 */
public class FruitController {

    private FruitService fruitService = null;
    protected String index(String oper, String keyword, Integer pageOn, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (pageOn == null){
            pageOn = 1;
        }

        if (StringUtils.isNotEmpty(oper) && "search".equals(oper)) {
            pageOn = 1;
            session.setAttribute("keyword", keyword);//保留有keyword
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageOn);
        int fruitCount = fruitService.getPageCount(keyword);

        session.setAttribute("pageOn", pageOn);
        session.setAttribute("fruitCount", fruitCount);
        session.setAttribute("fruitList", fruitList);
        return "index";
    }

    protected String add(String fname, Integer price, Integer fcount, String remark) {
        fruitService.addFruit(new Fruit(0, fname, price, fcount, remark));
        return "redirect:fruit.do";
    }

    protected String del(Integer fid) {
        if (fid != null) {
            fruitService.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    protected String edit(HttpServletRequest req, Integer fid) {
        if (fid != null) {
            Fruit fruit = fruitService.getFruitById(fid);
            req.getSession().setAttribute("fruit", fruit);
            return "edit";
        }
        return "error";
    }

    protected String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        fruitService.updateFruit(new Fruit(fid, fname, price, fcount, remark));
        return "redirect:fruit.do";
    }

    protected String write() {
        return "add";
    }
}
