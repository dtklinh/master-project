/*     */ package Jama;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CholeskyDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] L;
/*     */   private int n;
/*     */   private boolean isspd;
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public CholeskyDecomposition(Matrix paramMatrix)
/*     */   {
/*  47 */     double[][] arrayOfDouble = paramMatrix.getArray();
/*  48 */     this.n = paramMatrix.getRowDimension();
/*  49 */     this.L = new double[this.n][this.n];
/*  50 */     this.isspd = (paramMatrix.getColumnDimension() == this.n);
/*     */ 
/*  52 */     for (int i = 0; i < this.n; i++) {
/*  53 */       double[] arrayOfDouble1 = this.L[i];
/*  54 */       double d1 = 0.0D;
/*  55 */       for (int j = 0; j < i; j++) {
/*  56 */         double[] arrayOfDouble2 = this.L[j];
/*  57 */         double d2 = 0.0D;
/*  58 */         for (int k = 0; k < j; k++)
/*  59 */           d2 += arrayOfDouble2[k] * arrayOfDouble1[k];
/*     */         double tmp151_150 = ((arrayOfDouble[i][j] - d2) / this.L[j][j]); d2 = tmp151_150; arrayOfDouble1[j] = tmp151_150;
/*  62 */         d1 += d2 * d2;
/*  63 */         this.isspd &= arrayOfDouble[j][i] == arrayOfDouble[i][j];
/*     */       }
/*  65 */       d1 = arrayOfDouble[i][i] - d1;
/*  66 */       this.isspd &= d1 > 0.0D;
/*  67 */       this.L[i][i] = Math.sqrt(Math.max(d1, 0.0D));
/*  68 */       for (int j = i + 1; j < this.n; j++)
/*  69 */         this.L[i][j] = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isSPD()
/*     */   {
/* 145 */     return this.isspd;
/*     */   }
/*     */ 
/*     */   public Matrix getL()
/*     */   {
/* 153 */     return new Matrix(this.L, this.n, this.n);
/*     */   }
/*     */ 
/*     */   public Matrix solve(Matrix paramMatrix)
/*     */   {
/* 164 */     if (paramMatrix.getRowDimension() != this.n) {
/* 165 */       throw new IllegalArgumentException("Matrix row dimensions must agree.");
/*     */     }
/* 167 */     if (!this.isspd) {
/* 168 */       throw new RuntimeException("Matrix is not symmetric positive definite.");
/*     */     }
/*     */ 
/* 172 */     double[][] arrayOfDouble = paramMatrix.getArrayCopy();
/* 173 */     int i = paramMatrix.getColumnDimension();
/*     */     int k;
/*     */     int m;
/* 176 */     for (int j = 0; j < this.n; j++) {
/* 177 */       for (k = 0; k < i; k++) {
/* 178 */         for (m = 0; m < j; m++) {
/* 179 */           arrayOfDouble[j][k] -= arrayOfDouble[m][k] * this.L[j][m];
/*     */         }
/* 181 */         arrayOfDouble[j][k] /= this.L[j][j];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 186 */     for (int j = this.n - 1; j >= 0; j--) {
/* 187 */       for (k = 0; k < i; k++) {
/* 188 */         for (m = j + 1; m < this.n; m++) {
/* 189 */           arrayOfDouble[j][k] -= arrayOfDouble[m][k] * this.L[m][j];
/*     */         }
/* 191 */         arrayOfDouble[j][k] /= this.L[j][j];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 196 */     return new Matrix(arrayOfDouble, this.n, i);
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.CholeskyDecomposition
 * JD-Core Version:    0.6.2
 */