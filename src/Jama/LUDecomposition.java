/*     */ package Jama;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class LUDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] LU;
/*     */   private int m;
/*     */   private int n;
/*     */   private int pivsign;
/*     */   private int[] piv;
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public LUDecomposition(Matrix paramMatrix)
/*     */   {
/*  52 */     this.LU = paramMatrix.getArrayCopy();
/*  53 */     this.m = paramMatrix.getRowDimension();
/*  54 */     this.n = paramMatrix.getColumnDimension();
/*  55 */     this.piv = new int[this.m];
/*  56 */     for (int i = 0; i < this.m; i++) {
/*  57 */       this.piv[i] = i;
/*     */     }
/*  59 */     this.pivsign = 1;
/*     */ 
/*  61 */     double[] arrayOfDouble2 = new double[this.m];
/*     */ 
/*  65 */     for (int j = 0; j < this.n; j++)
/*     */     {
/*  69 */       for (int k = 0; k < this.m; k++)
/*  70 */         arrayOfDouble2[k] = this.LU[k][j];
/*     */       double d;
/*  75 */       for (int k = 0; k < this.m; k++) {
/*  76 */         double[] arrayOfDouble1 = this.LU[k];
/*     */ 
/*  80 */         int i1 = Math.min(k, j);
/*  81 */         d = 0.0D;
/*  82 */         for (int i2 = 0; i2 < i1; i2++)
/*  83 */           d += arrayOfDouble1[i2] * arrayOfDouble2[i2];
/*     */         int tmp185_183 = k;
/*     */         double[] tmp185_182 = arrayOfDouble2;
/*     */         long tmp190_189 = (long)(tmp185_182[tmp185_183] - d); tmp185_182[tmp185_183] = tmp190_189; arrayOfDouble1[j] = tmp190_189;
/*     */       }
/*     */ 
/*  91 */       int k = j;
/*  92 */       for (int i1 = j + 1; i1 < this.m; i1++) {
/*  93 */         if (Math.abs(arrayOfDouble2[i1]) > Math.abs(arrayOfDouble2[k])) {
/*  94 */           k = i1;
/*     */         }
/*     */       }
/*  97 */       if (k != j) {
/*  98 */         for (int i1 = 0; i1 < this.n; i1++) {
/*  99 */           d = this.LU[k][i1]; this.LU[k][i1] = this.LU[j][i1]; this.LU[j][i1] = d;
/*     */         }
/* 101 */         int i1 = this.piv[k]; this.piv[k] = this.piv[j]; this.piv[j] = i1;
/* 102 */         this.pivsign = (-this.pivsign);
/*     */       }
/*     */ 
/* 107 */       if (((j < this.m ? 1 : 0) & (this.LU[j][j] != 0.0D ? 1 : 0)) != 0)
/* 108 */         for (int i1 = j + 1; i1 < this.m; i1++)
/* 109 */           this.LU[i1][j] /= this.LU[j][j];
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isNonsingular()
/*     */   {
/* 183 */     for (int i = 0; i < this.n; i++) {
/* 184 */       if (this.LU[i][i] == 0.0D)
/* 185 */         return false;
/*     */     }
/* 187 */     return true;
/*     */   }
/*     */ 
/*     */   public Matrix getL()
/*     */   {
/* 195 */     Matrix localMatrix = new Matrix(this.m, this.n);
/* 196 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 197 */     for (int i = 0; i < this.m; i++) {
/* 198 */       for (int j = 0; j < this.n; j++) {
/* 199 */         if (i > j)
/* 200 */           arrayOfDouble[i][j] = this.LU[i][j];
/* 201 */         else if (i == j)
/* 202 */           arrayOfDouble[i][j] = 1.0D;
/*     */         else {
/* 204 */           arrayOfDouble[i][j] = 0.0D;
/*     */         }
/*     */       }
/*     */     }
/* 208 */     return localMatrix;
/*     */   }
/*     */ 
/*     */   public Matrix getU()
/*     */   {
/* 216 */     Matrix localMatrix = new Matrix(this.n, this.n);
/* 217 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 218 */     for (int i = 0; i < this.n; i++) {
/* 219 */       for (int j = 0; j < this.n; j++) {
/* 220 */         if (i <= j)
/* 221 */           arrayOfDouble[i][j] = this.LU[i][j];
/*     */         else {
/* 223 */           arrayOfDouble[i][j] = 0.0D;
/*     */         }
/*     */       }
/*     */     }
/* 227 */     return localMatrix;
/*     */   }
/*     */ 
/*     */   public int[] getPivot()
/*     */   {
/* 235 */     int[] arrayOfInt = new int[this.m];
/* 236 */     for (int i = 0; i < this.m; i++) {
/* 237 */       arrayOfInt[i] = this.piv[i];
/*     */     }
/* 239 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public double[] getDoublePivot()
/*     */   {
/* 247 */     double[] arrayOfDouble = new double[this.m];
/* 248 */     for (int i = 0; i < this.m; i++) {
/* 249 */       arrayOfDouble[i] = this.piv[i];
/*     */     }
/* 251 */     return arrayOfDouble;
/*     */   }
/*     */ 
/*     */   public double det()
/*     */   {
/* 260 */     if (this.m != this.n) {
/* 261 */       throw new IllegalArgumentException("Matrix must be square.");
/*     */     }
/* 263 */     double d = this.pivsign;
/* 264 */     for (int i = 0; i < this.n; i++) {
/* 265 */       d *= this.LU[i][i];
/*     */     }
/* 267 */     return d;
/*     */   }
/*     */ 
/*     */   public Matrix solve(Matrix paramMatrix)
/*     */   {
/* 278 */     if (paramMatrix.getRowDimension() != this.m) {
/* 279 */       throw new IllegalArgumentException("Matrix row dimensions must agree.");
/*     */     }
/* 281 */     if (!isNonsingular()) {
/* 282 */       throw new RuntimeException("Matrix is singular.");
/*     */     }
/*     */ 
/* 286 */     int i = paramMatrix.getColumnDimension();
/* 287 */     Matrix localMatrix = paramMatrix.getMatrix(this.piv, 0, i - 1);
/* 288 */     double[][] arrayOfDouble = localMatrix.getArray();
/*     */     int k;
/*     */     int i1;
/* 291 */     for (int j = 0; j < this.n; j++) {
/* 292 */       for (k = j + 1; k < this.n; k++) {
/* 293 */         for (i1 = 0; i1 < i; i1++) {
/* 294 */           arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * this.LU[k][j];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 299 */     for (int j = this.n - 1; j >= 0; j--) {
/* 300 */       for (k = 0; k < i; k++) {
/* 301 */         arrayOfDouble[j][k] /= this.LU[j][j];
/*     */       }
/* 303 */       for (k = 0; k < j; k++) {
/* 304 */         for (i1 = 0; i1 < i; i1++) {
/* 305 */           arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * this.LU[k][j];
/*     */         }
/*     */       }
/*     */     }
/* 309 */     return localMatrix;
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.LUDecomposition
 * JD-Core Version:    0.6.2
 */