package io.psol.tbtb.tbtb.DAO;

import io.psol.tbtb.tbtb.model.TBModel;

import java.util.List;

public interface TBDao {
    public void insert(TBModel tbModel);
    List<TBModel> selectAll() throws Exception;
}
