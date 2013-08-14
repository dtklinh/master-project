/*      */ package Jama;
/*      */ 
/*      */ import Jama.util.Maths;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamTokenizer;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ 
/*      */ public class Matrix
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private double[][] A;
/*      */   private int m;
/*      */   private int n;
/*      */   private static final long serialVersionUID = 1L;
/*      */ 
/*      */   public Matrix(int paramInt1, int paramInt2)
/*      */   {
/*   83 */     this.m = paramInt1;
/*   84 */     this.n = paramInt2;
/*   85 */     this.A = new double[paramInt1][paramInt2];
/*      */   }
/*      */ 
/*      */   public Matrix(int paramInt1, int paramInt2, double paramDouble)
/*      */   {
/*   95 */     this.m = paramInt1;
/*   96 */     this.n = paramInt2;
/*   97 */     this.A = new double[paramInt1][paramInt2];
/*   98 */     for (int i = 0; i < paramInt1; i++)
/*   99 */       for (int j = 0; j < paramInt2; j++)
/*  100 */         this.A[i][j] = paramDouble;
/*      */   }
/*      */ 
/*      */   public Matrix(double[][] paramArrayOfDouble)
/*      */   {
/*  112 */     this.m = paramArrayOfDouble.length;
/*  113 */     this.n = paramArrayOfDouble[0].length;
/*  114 */     for (int i = 0; i < this.m; i++) {
/*  115 */       if (paramArrayOfDouble[i].length != this.n) {
/*  116 */         throw new IllegalArgumentException("All rows must have the same length.");
/*      */       }
/*      */     }
/*  119 */     this.A = paramArrayOfDouble;
/*      */   }
            public Matrix(int[][] paramArrayOfDouble){
                this.m = paramArrayOfDouble.length;
/*  113 */     this.n = paramArrayOfDouble[0].length;
                double[][] tmp = new double[m][n];
/*  114 */     for (int i = 0; i < this.m; i++) {
/*  115 */       for(int j=0;j<n;j++){
                    tmp[i][j] = (double)paramArrayOfDouble[i][j];
                }
/*      */     }
/*  119 */     this.A = tmp;
            }
/*      */ 
/*      */   public Matrix(double[][] paramArrayOfDouble, int paramInt1, int paramInt2)
/*      */   {
/*  129 */     this.A = paramArrayOfDouble;
/*  130 */     this.m = paramInt1;
/*  131 */     this.n = paramInt2;
/*      */   }
/*      */ 
/*      */   public Matrix(double[] paramArrayOfDouble, int paramInt)
/*      */   {
/*  141 */     this.m = paramInt;
/*  142 */     this.n = (paramInt != 0 ? paramArrayOfDouble.length / paramInt : 0);
/*  143 */     if (paramInt * this.n != paramArrayOfDouble.length) {
/*  144 */       throw new IllegalArgumentException("Array length must be a multiple of m.");
/*      */     }
/*  146 */     this.A = new double[paramInt][this.n];
/*  147 */     for (int i = 0; i < paramInt; i++)
/*  148 */       for (int j = 0; j < this.n; j++)
/*  149 */         this.A[i][j] = paramArrayOfDouble[(i + j * paramInt)];
/*      */   }
/*      */ 
/*      */   public static Matrix constructWithCopy(double[][] paramArrayOfDouble)
/*      */   {
/*  164 */     int i = paramArrayOfDouble.length;
/*  165 */     int j = paramArrayOfDouble[0].length;
/*  166 */     Matrix localMatrix = new Matrix(i, j);
/*  167 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  168 */     for (int k = 0; k < i; k++) {
/*  169 */       if (paramArrayOfDouble[k].length != j) {
/*  170 */         throw new IllegalArgumentException("All rows must have the same length.");
/*      */       }
/*      */ 
/*  173 */       for (int i1 = 0; i1 < j; i1++) {
/*  174 */         arrayOfDouble[k][i1] = paramArrayOfDouble[k][i1];
/*      */       }
/*      */     }
/*  177 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix copy()
/*      */   {
/*  184 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  185 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  186 */     for (int i = 0; i < this.m; i++) {
/*  187 */       for (int j = 0; j < this.n; j++) {
/*  188 */         arrayOfDouble[i][j] = this.A[i][j];
/*      */       }
/*      */     }
/*  191 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Object clone()
/*      */   {
/*  198 */     return copy();
/*      */   }
/*      */ 
/*      */   public double[][] getArray()
/*      */   {
/*  206 */     return this.A;
/*      */   }
/*      */ 
/*      */   public double[][] getArrayCopy()
/*      */   {
/*  214 */     double[][] arrayOfDouble = new double[this.m][this.n];
/*  215 */     for (int i = 0; i < this.m; i++) {
/*  216 */       for (int j = 0; j < this.n; j++) {
/*  217 */         arrayOfDouble[i][j] = this.A[i][j];
/*      */       }
/*      */     }
/*  220 */     return arrayOfDouble;
/*      */   }
/*      */ 
/*      */   public double[] getColumnPackedCopy()
/*      */   {
/*  228 */     double[] arrayOfDouble = new double[this.m * this.n];
/*  229 */     for (int i = 0; i < this.m; i++) {
/*  230 */       for (int j = 0; j < this.n; j++) {
/*  231 */         arrayOfDouble[(i + j * this.m)] = this.A[i][j];
/*      */       }
/*      */     }
/*  234 */     return arrayOfDouble;
/*      */   }
/*      */ 
/*      */   public double[] getRowPackedCopy()
/*      */   {
/*  242 */     double[] arrayOfDouble = new double[this.m * this.n];
/*  243 */     for (int i = 0; i < this.m; i++) {
/*  244 */       for (int j = 0; j < this.n; j++) {
/*  245 */         arrayOfDouble[(i * this.n + j)] = this.A[i][j];
/*      */       }
/*      */     }
/*  248 */     return arrayOfDouble;
/*      */   }
/*      */ 
/*      */   public int getRowDimension()
/*      */   {
/*  256 */     return this.m;
/*      */   }
/*      */ 
/*      */   public int getColumnDimension()
/*      */   {
/*  264 */     return this.n;
/*      */   }
/*      */ 
/*      */   public double get(int paramInt1, int paramInt2)
/*      */   {
/*  275 */     return this.A[paramInt1][paramInt2];
/*      */   }
/*      */ 
/*      */   public Matrix getMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  288 */     Matrix localMatrix = new Matrix(paramInt2 - paramInt1 + 1, paramInt4 - paramInt3 + 1);
/*  289 */     double[][] arrayOfDouble = localMatrix.getArray();
/*      */     try {
/*  291 */       for (int i = paramInt1; i <= paramInt2; i++)
/*  292 */         for (int j = paramInt3; j <= paramInt4; j++)
/*  293 */           arrayOfDouble[(i - paramInt1)][(j - paramInt3)] = this.A[i][j];
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  297 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*  299 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix getMatrix(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  310 */     Matrix localMatrix = new Matrix(paramArrayOfInt1.length, paramArrayOfInt2.length);
/*  311 */     double[][] arrayOfDouble = localMatrix.getArray();
/*      */     try {
/*  313 */       for (int i = 0; i < paramArrayOfInt1.length; i++)
/*  314 */         for (int j = 0; j < paramArrayOfInt2.length; j++)
/*  315 */           arrayOfDouble[i][j] = this.A[paramArrayOfInt1[i]][paramArrayOfInt2[j]];
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  319 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*  321 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix getMatrix(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*      */   {
/*  333 */     Matrix localMatrix = new Matrix(paramInt2 - paramInt1 + 1, paramArrayOfInt.length);
/*  334 */     double[][] arrayOfDouble = localMatrix.getArray();
/*      */     try {
/*  336 */       for (int i = paramInt1; i <= paramInt2; i++)
/*  337 */         for (int j = 0; j < paramArrayOfInt.length; j++)
/*  338 */           arrayOfDouble[(i - paramInt1)][j] = this.A[i][paramArrayOfInt[j]];
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  342 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*  344 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix getMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/*  356 */     Matrix localMatrix = new Matrix(paramArrayOfInt.length, paramInt2 - paramInt1 + 1);
/*  357 */     double[][] arrayOfDouble = localMatrix.getArray();
/*      */     try {
/*  359 */       for (int i = 0; i < paramArrayOfInt.length; i++)
/*  360 */         for (int j = paramInt1; j <= paramInt2; j++)
/*  361 */           arrayOfDouble[i][(j - paramInt1)] = this.A[paramArrayOfInt[i]][j];
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  365 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*  367 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public void set(int paramInt1, int paramInt2, double paramDouble)
/*      */   {
/*  378 */     this.A[paramInt1][paramInt2] = paramDouble;
/*      */   }
/*      */ 
/*      */   public void setMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Matrix paramMatrix)
/*      */   {
/*      */     try
/*      */     {
/*  392 */       for (int i = paramInt1; i <= paramInt2; i++)
/*  393 */         for (int j = paramInt3; j <= paramInt4; j++)
/*  394 */           this.A[i][j] = paramMatrix.get(i - paramInt1, j - paramInt3);
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  398 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMatrix(int[] paramArrayOfInt1, int[] paramArrayOfInt2, Matrix paramMatrix)
/*      */   {
/*      */     try
/*      */     {
/*  411 */       for (int i = 0; i < paramArrayOfInt1.length; i++)
/*  412 */         for (int j = 0; j < paramArrayOfInt2.length; j++)
/*  413 */           this.A[paramArrayOfInt1[i]][paramArrayOfInt2[j]] = paramMatrix.get(i, j);
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  417 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2, Matrix paramMatrix)
/*      */   {
/*      */     try
/*      */     {
/*  431 */       for (int i = 0; i < paramArrayOfInt.length; i++)
/*  432 */         for (int j = paramInt1; j <= paramInt2; j++)
/*  433 */           this.A[paramArrayOfInt[i]][j] = paramMatrix.get(i, j - paramInt1);
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  437 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMatrix(int paramInt1, int paramInt2, int[] paramArrayOfInt, Matrix paramMatrix)
/*      */   {
/*      */     try
/*      */     {
/*  451 */       for (int i = paramInt1; i <= paramInt2; i++)
/*  452 */         for (int j = 0; j < paramArrayOfInt.length; j++)
/*  453 */           this.A[i][paramArrayOfInt[j]] = paramMatrix.get(i - paramInt1, j);
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*  457 */       throw new ArrayIndexOutOfBoundsException("Submatrix indices");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Matrix transpose()
/*      */   {
/*  466 */     Matrix localMatrix = new Matrix(this.n, this.m);
/*  467 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  468 */     for (int i = 0; i < this.m; i++) {
/*  469 */       for (int j = 0; j < this.n; j++) {
/*  470 */         arrayOfDouble[j][i] = this.A[i][j];
/*      */       }
/*      */     }
/*  473 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public double norm1()
/*      */   {
/*  481 */     double d1 = 0.0D;
/*  482 */     for (int i = 0; i < this.n; i++) {
/*  483 */       double d2 = 0.0D;
/*  484 */       for (int j = 0; j < this.m; j++) {
/*  485 */         d2 += Math.abs(this.A[j][i]);
/*      */       }
/*  487 */       d1 = Math.max(d1, d2);
/*      */     }
/*  489 */     return d1;
/*      */   }
/*      */ 
/*      */   public double norm2()
/*      */   {
/*  497 */     return new SingularValueDecomposition(this).norm2();
/*      */   }
/*      */ 
/*      */   public double normInf()
/*      */   {
/*  505 */     double d1 = 0.0D;
/*  506 */     for (int i = 0; i < this.m; i++) {
/*  507 */       double d2 = 0.0D;
/*  508 */       for (int j = 0; j < this.n; j++) {
/*  509 */         d2 += Math.abs(this.A[i][j]);
/*      */       }
/*  511 */       d1 = Math.max(d1, d2);
/*      */     }
/*  513 */     return d1;
/*      */   }
/*      */ 
/*      */   public double normF()
/*      */   {
/*  521 */     double d = 0.0D;
/*  522 */     for (int i = 0; i < this.m; i++) {
/*  523 */       for (int j = 0; j < this.n; j++) {
/*  524 */         d = Maths.hypot(d, this.A[i][j]);
/*      */       }
/*      */     }
/*  527 */     return d;
/*      */   }
/*      */ 
/*      */   public Matrix uminus()
/*      */   {
/*  535 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  536 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  537 */     for (int i = 0; i < this.m; i++) {
/*  538 */       for (int j = 0; j < this.n; j++) {
/*  539 */         arrayOfDouble[i][j] = (-this.A[i][j]);
/*      */       }
/*      */     }
/*  542 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix plus(Matrix paramMatrix)
/*      */   {
/*  551 */     checkMatrixDimensions(paramMatrix);
/*  552 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  553 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  554 */     for (int i = 0; i < this.m; i++) {
/*  555 */       for (int j = 0; j < this.n; j++) {
/*  556 */         arrayOfDouble[i][j] = (this.A[i][j] + paramMatrix.A[i][j]);
/*      */       }
/*      */     }
/*  559 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix plusEquals(Matrix paramMatrix)
/*      */   {
/*  568 */     checkMatrixDimensions(paramMatrix);
/*  569 */     for (int i = 0; i < this.m; i++) {
/*  570 */       for (int j = 0; j < this.n; j++) {
/*  571 */         this.A[i][j] += paramMatrix.A[i][j];
/*      */       }
/*      */     }
/*  574 */     return this;
/*      */   }
/*      */ 
/*      */   public Matrix minus(Matrix paramMatrix)
/*      */   {
/*  583 */     checkMatrixDimensions(paramMatrix);
/*  584 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  585 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  586 */     for (int i = 0; i < this.m; i++) {
/*  587 */       for (int j = 0; j < this.n; j++) {
/*  588 */         arrayOfDouble[i][j] = (this.A[i][j] - paramMatrix.A[i][j]);
/*      */       }
/*      */     }
/*  591 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix minusEquals(Matrix paramMatrix)
/*      */   {
/*  600 */     checkMatrixDimensions(paramMatrix);
/*  601 */     for (int i = 0; i < this.m; i++) {
/*  602 */       for (int j = 0; j < this.n; j++) {
/*  603 */         this.A[i][j] -= paramMatrix.A[i][j];
/*      */       }
/*      */     }
/*  606 */     return this;
/*      */   }
/*      */ 
/*      */   public Matrix arrayTimes(Matrix paramMatrix)
/*      */   {
/*  615 */     checkMatrixDimensions(paramMatrix);
/*  616 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  617 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  618 */     for (int i = 0; i < this.m; i++) {
/*  619 */       for (int j = 0; j < this.n; j++) {
/*  620 */         arrayOfDouble[i][j] = (this.A[i][j] * paramMatrix.A[i][j]);
/*      */       }
/*      */     }
/*  623 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix arrayTimesEquals(Matrix paramMatrix)
/*      */   {
/*  632 */     checkMatrixDimensions(paramMatrix);
/*  633 */     for (int i = 0; i < this.m; i++) {
/*  634 */       for (int j = 0; j < this.n; j++) {
/*  635 */         this.A[i][j] *= paramMatrix.A[i][j];
/*      */       }
/*      */     }
/*  638 */     return this;
/*      */   }
/*      */ 
/*      */   public Matrix arrayRightDivide(Matrix paramMatrix)
/*      */   {
/*  647 */     checkMatrixDimensions(paramMatrix);
/*  648 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  649 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  650 */     for (int i = 0; i < this.m; i++) {
/*  651 */       for (int j = 0; j < this.n; j++) {
/*  652 */         arrayOfDouble[i][j] = (this.A[i][j] / paramMatrix.A[i][j]);
/*      */       }
/*      */     }
/*  655 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix arrayRightDivideEquals(Matrix paramMatrix)
/*      */   {
/*  664 */     checkMatrixDimensions(paramMatrix);
/*  665 */     for (int i = 0; i < this.m; i++) {
/*  666 */       for (int j = 0; j < this.n; j++) {
/*  667 */         this.A[i][j] /= paramMatrix.A[i][j];
/*      */       }
/*      */     }
/*  670 */     return this;
/*      */   }
/*      */ 
/*      */   public Matrix arrayLeftDivide(Matrix paramMatrix)
/*      */   {
/*  679 */     checkMatrixDimensions(paramMatrix);
/*  680 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  681 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  682 */     for (int i = 0; i < this.m; i++) {
/*  683 */       for (int j = 0; j < this.n; j++) {
/*  684 */         arrayOfDouble[i][j] = (paramMatrix.A[i][j] / this.A[i][j]);
/*      */       }
/*      */     }
/*  687 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix arrayLeftDivideEquals(Matrix paramMatrix)
/*      */   {
/*  696 */     checkMatrixDimensions(paramMatrix);
/*  697 */     for (int i = 0; i < this.m; i++) {
/*  698 */       for (int j = 0; j < this.n; j++) {
/*  699 */         paramMatrix.A[i][j] /= this.A[i][j];
/*      */       }
/*      */     }
/*  702 */     return this;
/*      */   }
/*      */ 
/*      */   public Matrix times(double paramDouble)
/*      */   {
/*  711 */     Matrix localMatrix = new Matrix(this.m, this.n);
/*  712 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  713 */     for (int i = 0; i < this.m; i++) {
/*  714 */       for (int j = 0; j < this.n; j++) {
/*  715 */         arrayOfDouble[i][j] = (paramDouble * this.A[i][j]);
/*      */       }
/*      */     }
/*  718 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public Matrix timesEquals(double paramDouble)
/*      */   {
/*  727 */     for (int i = 0; i < this.m; i++) {
/*  728 */       for (int j = 0; j < this.n; j++) {
/*  729 */         this.A[i][j] = (paramDouble * this.A[i][j]);
/*      */       }
/*      */     }
/*  732 */     return this;
/*      */   }
/*      */ 
/*      */   public Matrix times(Matrix paramMatrix)
/*      */   {
/*  742 */     if (paramMatrix.m != this.n) {
/*  743 */       throw new IllegalArgumentException("Matrix inner dimensions must agree.");
/*      */     }
/*  745 */     Matrix localMatrix = new Matrix(this.m, paramMatrix.n);
/*  746 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  747 */     double[] arrayOfDouble1 = new double[this.n];
/*  748 */     for (int i = 0; i < paramMatrix.n; i++) {
/*  749 */       for (int j = 0; j < this.n; j++) {
/*  750 */         arrayOfDouble1[j] = paramMatrix.A[j][i];
/*      */       }
/*  752 */       for (int j = 0; j < this.m; j++) {
/*  753 */         double[] arrayOfDouble2 = this.A[j];
/*  754 */         double d = 0.0D;
/*  755 */         for (int k = 0; k < this.n; k++) {
/*  756 */           d += arrayOfDouble2[k] * arrayOfDouble1[k];
/*      */         }
/*  758 */         arrayOfDouble[j][i] = d;
/*      */       }
/*      */     }
/*  761 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public LUDecomposition lu()
/*      */   {
/*  770 */     return new LUDecomposition(this);
/*      */   }
/*      */ 
/*      */   public QRDecomposition qr()
/*      */   {
/*  779 */     return new QRDecomposition(this);
/*      */   }
/*      */ 
/*      */   public CholeskyDecomposition chol()
/*      */   {
/*  788 */     return new CholeskyDecomposition(this);
/*      */   }
/*      */ 
/*      */   public SingularValueDecomposition svd()
/*      */   {
/*  797 */     return new SingularValueDecomposition(this);
/*      */   }
/*      */ 
/*      */   public EigenvalueDecomposition eig()
/*      */   {
/*  806 */     return new EigenvalueDecomposition(this);
/*      */   }
/*      */ 
/*      */   public Matrix solve(Matrix paramMatrix)
/*      */   {
/*  815 */     return this.m == this.n ? new LUDecomposition(this).solve(paramMatrix) : new QRDecomposition(this).solve(paramMatrix);
/*      */   }
/*      */ 
/*      */   public Matrix solveTranspose(Matrix paramMatrix)
/*      */   {
/*  825 */     return transpose().solve(paramMatrix.transpose());
/*      */   }
/*      */ 
/*      */   public Matrix inverse()
/*      */   {
/*  833 */     return solve(identity(this.m, this.m));
/*      */   }
/*      */ 
/*      */   public double det()
/*      */   {
/*  841 */     return new LUDecomposition(this).det();
/*      */   }
/*      */ 
/*      */   public int rank()
/*      */   {
/*  849 */     return new SingularValueDecomposition(this).rank();
/*      */   }
/*      */ 
/*      */   public double cond()
/*      */   {
/*  857 */     return new SingularValueDecomposition(this).cond();
/*      */   }
/*      */ 
/*      */   public double trace()
/*      */   {
/*  865 */     double d = 0.0D;
/*  866 */     for (int i = 0; i < Math.min(this.m, this.n); i++) {
/*  867 */       d += this.A[i][i];
/*      */     }
/*  869 */     return d;
/*      */   }
/*      */ 
/*      */   public static Matrix random(int paramInt1, int paramInt2)
/*      */   {
/*  879 */     Matrix localMatrix = new Matrix(paramInt1, paramInt2);
/*  880 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  881 */     for (int i = 0; i < paramInt1; i++) {
/*  882 */       for (int j = 0; j < paramInt2; j++) {
/*  883 */         arrayOfDouble[i][j] = Math.random();
/*      */       }
/*      */     }
/*  886 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public static Matrix identity(int paramInt1, int paramInt2)
/*      */   {
/*  896 */     Matrix localMatrix = new Matrix(paramInt1, paramInt2);
/*  897 */     double[][] arrayOfDouble = localMatrix.getArray();
/*  898 */     for (int i = 0; i < paramInt1; i++) {
/*  899 */       for (int j = 0; j < paramInt2; j++) {
/*  900 */         arrayOfDouble[i][j] = (i == j ? 1.0D : 0.0D);
/*      */       }
/*      */     }
/*  903 */     return localMatrix;
/*      */   }
/*      */ 
/*      */   public void print(int paramInt1, int paramInt2)
/*      */   {
/*  914 */     print(new PrintWriter(System.out, true), paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void print(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
/*      */   {
/*  924 */     DecimalFormat localDecimalFormat = new DecimalFormat();
/*  925 */     localDecimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
/*  926 */     localDecimalFormat.setMinimumIntegerDigits(1);
/*  927 */     localDecimalFormat.setMaximumFractionDigits(paramInt2);
/*  928 */     localDecimalFormat.setMinimumFractionDigits(paramInt2);
/*  929 */     localDecimalFormat.setGroupingUsed(false);
/*  930 */     print(paramPrintWriter, localDecimalFormat, paramInt1 + 2);
/*      */   }
/*      */ 
/*      */   public void print(NumberFormat paramNumberFormat, int paramInt)
/*      */   {
/*  944 */     print(new PrintWriter(System.out, true), paramNumberFormat, paramInt);
/*      */   }
/*      */ 
/*      */   public void print(PrintWriter paramPrintWriter, NumberFormat paramNumberFormat, int paramInt)
/*      */   {
/*  963 */     paramPrintWriter.println();
/*  964 */     for (int i = 0; i < this.m; i++) {
/*  965 */       for (int j = 0; j < this.n; j++) {
/*  966 */         String str = paramNumberFormat.format(this.A[i][j]);
/*  967 */         int k = Math.max(1, paramInt - str.length());
/*  968 */         for (int i1 = 0; i1 < k; i1++)
/*  969 */           paramPrintWriter.print(' ');
/*  970 */         paramPrintWriter.print(str);
/*      */       }
/*  972 */       paramPrintWriter.println();
/*      */     }
/*  974 */     paramPrintWriter.println();
/*      */   }
/*      */ 
/*      */   public static Matrix read(BufferedReader paramBufferedReader)
/*      */     throws IOException
/*      */   {
/*  986 */     StreamTokenizer localStreamTokenizer = new StreamTokenizer(paramBufferedReader);
/*      */ 
/*  994 */     localStreamTokenizer.resetSyntax();
/*  995 */     localStreamTokenizer.wordChars(0, 255);
/*  996 */     localStreamTokenizer.whitespaceChars(0, 32);
/*  997 */     localStreamTokenizer.eolIsSignificant(true);
/*  998 */     Vector localVector1 = new Vector();
/*      */ 
/* 1001 */     while (localStreamTokenizer.nextToken() == 10);
/* 1002 */     if (localStreamTokenizer.ttype == -1)
/* 1003 */       throw new IOException("Unexpected EOF on matrix read.");
/*      */     do
/* 1005 */       localVector1.addElement(Double.valueOf(localStreamTokenizer.sval));
/* 1006 */     while (localStreamTokenizer.nextToken() == -3);
/*      */ 
/* 1008 */     int i = localVector1.size();
/* 1009 */     double[] arrayOfDouble = new double[i];
/* 1010 */     for (int j = 0; j < i; j++)
/* 1011 */       arrayOfDouble[j] = ((Double)localVector1.elementAt(j)).doubleValue();
/* 1012 */     Vector localVector2 = new Vector();
/* 1013 */     localVector2.addElement(arrayOfDouble);
/* 1014 */     while (localStreamTokenizer.nextToken() == -3)
/*      */     {
/* 1016 */       localVector2.addElement(arrayOfDouble = new double[i]);
/* 1017 */       int k = 0;
/*      */       do {
/* 1019 */         if (k >= i) throw new IOException("Row " + localVector2.size() + " is too long.");
/*      */ 
/* 1021 */         arrayOfDouble[(k++)] = Double.valueOf(localStreamTokenizer.sval).doubleValue();
/* 1022 */       }while (localStreamTokenizer.nextToken() == -3);
/* 1023 */       if (k < i) throw new IOException("Row " + localVector2.size() + " is too short.");
/*      */     }
/*      */ 
/* 1026 */     int k = localVector2.size();
/* 1027 */     double[][] arrayOfDouble1 = new double[k][];
/* 1028 */     localVector2.copyInto(arrayOfDouble1);
/* 1029 */     return new Matrix(arrayOfDouble1);
/*      */   }
/*      */ 
/*      */   private void checkMatrixDimensions(Matrix paramMatrix)
/*      */   {
/* 1040 */     if ((paramMatrix.m != this.m) || (paramMatrix.n != this.n))
/* 1041 */       throw new IllegalArgumentException("Matrix dimensions must agree.");
/*      */   }
/*      */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.Matrix
 * JD-Core Version:    0.6.2
 */