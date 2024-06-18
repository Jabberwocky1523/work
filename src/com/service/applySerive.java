package com.service;

import java.util.List;
import com.bean.apply;
import com.dao.applyDao;

public interface applySerive {
    public List<apply> getapplie();

    public List<apply> getAppliesByid(int id);

    public int addapply(apply apply);

    public int deleteapply(String name, int id);

    public int update(apply apply);

    public int overrule(apply apply);
}
