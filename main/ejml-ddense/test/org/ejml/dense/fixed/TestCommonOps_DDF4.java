/*
 * Copyright (c) 2009-2020, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Efficient Java Matrix Library (EJML).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix4;
import org.ejml.data.DMatrix4x4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestCommonOps_DDF4 extends CompareFixedToCommonOps_DDRM {

    public TestCommonOps_DDF4() {
        super(CommonOps_DDF4.class);
    }

    @Test
    public void diag() {
        DMatrix4x4 m = new DMatrix4x4(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        DMatrix4 found = new DMatrix4();

        CommonOps_DDF4.diag(m,found);

        assertEquals(1,found.a1,UtilEjml.TEST_F64);
        assertEquals(6,found.a2,UtilEjml.TEST_F64);
        assertEquals(11,found.a3, UtilEjml.TEST_F64);
        assertEquals(16,found.a4,UtilEjml.TEST_F64);
    }



}
