package io.psol.tbtb.tbtb.service.Impl;

import io.psol.tbtb.tbtb.DAO.TBDao;
import io.psol.tbtb.tbtb.model.TBModel;
import io.psol.tbtb.tbtb.service.TBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TBServiceImpl implements TBService {
    @Autowired
    private TBDao dao;

    @Override
    public void insert(TBModel tbModel) {
        dao.insert(tbModel);
    }
}
