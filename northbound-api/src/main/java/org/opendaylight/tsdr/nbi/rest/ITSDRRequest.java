/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.tsdr.nbi.rest;
/**
 * @author Sharon Aicler(saichler@gmail.com)
 **/
public interface ITSDRRequest extends IRequest{
    public String getFrom();
    public String getUntil();
    public String getTarget();
    public String getMaxDataPoints();
    public String getFormat();
}