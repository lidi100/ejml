/*
 * Copyright (c) 2009-2017, Peter Abeles. All Rights Reserved.
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

package org.ejml.dense.row;

import org.ejml.data.DMatrixRow_F64;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_R64;

import java.util.Random;

/**
 * Generates random vectors based on a zero mean multivariate Gaussian distribution.  The covariance
 * matrix is provided in the constructor.
 */
public class CovarianceRandomDraw_R64 {
    private DMatrixRow_F64 A;
    private Random rand;
    private DMatrixRow_F64 r;

    /**
     * Creates a random distribution with the specified mean and covariance.  The references
     * to the variables are not saved, their value are copied.
     *
     * @param rand Used to create the random numbers for the draw. Reference is saved.
     * @param cov The covariance of the distribution.  Not modified.
     */
    public CovarianceRandomDraw_R64(Random rand , DMatrixRow_F64 cov )
    {
        r = new DMatrixRow_F64(cov.numRows,1);
        CholeskyDecompositionInner_R64 cholesky = new CholeskyDecompositionInner_R64( true);

        if( cholesky.inputModified() )
            cov = cov.copy();
        if( !cholesky.decompose(cov) )
            throw new RuntimeException("Decomposition failed!");

        A = cholesky.getT();
        this.rand = rand;
    }

    /**
     * Makes a draw on the distribution.  The results are added to parameter 'x'
     */
    public void next( DMatrixRow_F64 x )
    {
        for( int i = 0; i < r.numRows; i++ ) {
            r.set(i,0, (double)rand.nextGaussian());
        }

        CommonOps_R64.multAdd(A,r,x);
    }

    /**
     * Computes the likelihood of the random draw
     *
     * @return The likelihood.
     */
    public double computeLikelihoodP() {
        double ret = 1.0;

        for( int i = 0; i < r.numRows; i++ ) {
            double a = r.get(i,0);

            ret *= Math.exp(-a*a/2.0);
        }

        return ret;
    }
}