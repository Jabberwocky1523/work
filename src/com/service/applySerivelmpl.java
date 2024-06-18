package com.service;

import java.util.List;

import com.bean.apply;
import com.dao.applyDao;

public class applySerivelmpl implements applySerive {
    private applyDao dao = new applyDao();

    public List<apply> getapplie() {
        return dao.getapplie();
    }

    public List<apply> getAppliesByid(int id) {
        return dao.getAppliesByid(id);
    }

    public int addapply(apply apply) {
        return dao.addapply(apply);
    }

    public int deleteapply(String name, int id) {
        return dao.deleteapply(name, id);
    }

    public int update(apply apply) {
        return dao.update(apply);
    }

    public int overrule(apply apply){
        return dao.overrule(apply);
    }
}
