package io.psol.tbtb.tbtb.service;

import io.psol.tbtb.tbtb.model.TBModel;

import java.util.List;

public interface TBService {
    void insert(TBModel tbModel);
    List<TBModel> selectAll() throws Exception;
}
