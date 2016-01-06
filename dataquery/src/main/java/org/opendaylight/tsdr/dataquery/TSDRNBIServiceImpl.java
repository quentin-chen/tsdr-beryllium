/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tsdr.dataquery;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.StoreTSDRLogRecordInput;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.StoreTSDRLogRecordInputBuilder;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.StoreTSDRMetricRecordInput;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.StoreTSDRMetricRecordInputBuilder;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.TSDRService;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrlogrecord.input.TSDRLogRecord;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrlogrecord.input.TSDRLogRecordBuilder;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrmetricrecord.input.TSDRMetricRecord;
import org.opendaylight.yang.gen.v1.opendaylight.tsdr.rev150219.storetsdrmetricrecord.input.TSDRMetricRecordBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.tsdr.dataquery.impl.rev150219.AddLogInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.tsdr.dataquery.impl.rev150219.AddMetricInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.tsdr.dataquery.impl.rev150219.TSDRDataqueryImplService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sharon Aicler(saichler@gmail.com)
 **/
public class TSDRNBIServiceImpl implements TSDRDataqueryImplService {
    private static Logger logger = LoggerFactory.getLogger(TSDRNBIServiceImpl.class);
    private TSDRService tsdrService = null;
    // The reference to the the RPC registry to store the data
    private final RpcProviderRegistry rpcRegistry;

    public TSDRNBIServiceImpl(TSDRService _tsdrService, RpcProviderRegistry _rpcRegistry) {
        this.tsdrService = _tsdrService;
        this.rpcRegistry = _rpcRegistry;
    }

    @Override
    public Future<RpcResult<Void>> addMetric(AddMetricInput input) {
        TSDRMetricRecordBuilder b = new TSDRMetricRecordBuilder();
        b.setMetricName(input.getMetricName());
        b.setMetricValue(input.getMetricValue());
        b.setNodeID(input.getNodeID());
        b.setTimeStamp(input.getTimeStamp());
        b.setTSDRDataCategory(input.getTSDRDataCategory());
        b.setRecordKeys(input.getRecordKeys());
        StoreTSDRMetricRecordInputBuilder in = new StoreTSDRMetricRecordInputBuilder();
        List<TSDRMetricRecord> list = new LinkedList<>();
        list.add(b.build());
        in.setTSDRMetricRecord(list);
        store(in.build());
        RpcResultBuilder<Void> rpc = RpcResultBuilder.success();
        return rpc.buildFuture();
    }

    @Override
    public Future<RpcResult<Void>> addLog(AddLogInput input) {
        TSDRLogRecordBuilder b = new TSDRLogRecordBuilder();
        b.setRecordFullText(input.getRecordFullText());
        b.setNodeID(input.getNodeID());
        b.setTimeStamp(input.getTimeStamp());
        b.setTSDRDataCategory(input.getTSDRDataCategory());
        b.setRecordKeys(input.getRecordKeys());
        b.setRecordAttributes(input.getRecordAttributes());
        StoreTSDRLogRecordInputBuilder in = new StoreTSDRLogRecordInputBuilder();
        List<TSDRLogRecord> list = new LinkedList<>();
        list.add(b.build());
        in.setTSDRLogRecord(list);
        store(in.build());
        RpcResultBuilder<Void> rpc = RpcResultBuilder.success();
        return rpc.buildFuture();

    }

    // Invoke the storage rpc method
    private void store(StoreTSDRMetricRecordInput input) {
        if (tsdrService == null) {
            tsdrService = this.rpcRegistry.getRpcService(TSDRService.class);
        }
        tsdrService.storeTSDRMetricRecord(input);
        logger.debug("Data Storage called");
    }

    // Invoke the storage rpc method
    private void store(StoreTSDRLogRecordInput input) {
        if (tsdrService == null) {
            tsdrService = this.rpcRegistry.getRpcService(TSDRService.class);
        }
        tsdrService.storeTSDRLogRecord(input);
        logger.debug("Data Storage called");
    }
}