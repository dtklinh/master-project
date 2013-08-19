/*     */ package Jama;
/*     */ 
/*     */ import Jama.util.Maths;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class QRDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] QR;
/*     */   private int m;
/*     */   private int n;
/*     */   private double[] Rdiag;
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public QRDecomposition(Matrix paramMatrix)
/*     */   {
/*  50 */     this.QR = paramMatrix.getArrayCopy();
/*  51 */     this.m = paramMatrix.getRowDimension();
/*  52 */     this.n = paramMatrix.getColumnDimension();
/*  53 */     this.Rdiag = new double[this.n];
/*     */ 
/*  56 */     for (int i = 0; i < this.n; i++)
/*     */     {
/*  58 */       double d1 = 0.0D;
/*  59 */       for (int j = i; j < this.m; j++) {
/*  60 */         d1 = Maths.hypot(d1, this.QR[j][i]);
/*     */       }
/*     */ 
/*  63 */       if (d1 != 0.0D)
/*     */       {
/*  65 */         if (this.QR[i][i] < 0.0D) {
/*  66 */           d1 = -d1;
/*     */         }
/*  68 */         for (j = i; j < this.m; j++) {
/*  69 */           this.QR[j][i] /= d1;
/*     */         }
/*  71 */         this.QR[i][i] += 1.0D;
/*     */ 
/*  74 */         for (j = i + 1; j < this.n; j++) {
/*  75 */           double d2 = 0.0D;
/*  76 */           for (int k = i; k < this.m; k++) {
/*  77 */             d2 += this.QR[k][i] * this.QR[k][j];
/*     */           }
/*  79 */           d2 = -d2 / this.QR[i][i];
/*  80 */           for (k = i; k < this.m; k++) {
/*  81 */             this.QR[k][j] += d2 * this.QR[k][i];
/*     */           }
/*     */         }
/*     */       }
/*  85 */       this.Rdiag[i] = (-d1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isFullRank()
/*     */   {
/*  98 */     for (int i = 0; i < this.n; i++) {
/*  99 */       if (this.Rdiag[i] == 0.0D)
/* 100 */         return false;
/*     */     }
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   public Matrix getH()
/*     */   {
/* 110 */     Matrix localMatrix = new Matrix(this.m, this.n);
/* 111 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 112 */     for (int i = 0; i < this.m; i++) {
/* 113 */       for (int j = 0; j < this.n; j++) {
/* 114 */         if (i >= j)
/* 115 */           arrayOfDouble[i][j] = this.QR[i][j];
/*     */         else {
/* 117 */           arrayOfDouble[i][j] = 0.0D;
/*     */         }
/*     */       }
/*     */     }
/* 121 */     return localMatrix;
/*     */   }
/*     */ 
/*     */   public Matrix getR()
/*     */   {
/* 129 */     Matrix localMatrix = new Matrix(this.n, this.n);
/* 130 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 131 */     for (int i = 0; i < this.n; i++) {
/* 132 */       for (int j = 0; j < this.n; j++) {
/* 133 */         if (i < j)
/* 134 */           arrayOfDouble[i][j] = this.QR[i][j];
/* 135 */         else if (i == j)
/* 136 */           arrayOfDouble[i][j] = this.Rdiag[i];
/*     */         else {
/* 138 */           arrayOfDouble[i][j] = 0.0D;
/*     */         }
/*     */       }
/*     */     }
/* 142 */     return localMatrix;
/*     */   }
/*     */ 
/*     */   public Matrix getQ()
/*     */   {
/* 150 */     Matrix localMatrix = new Matrix(this.m, this.n);
/* 151 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 152 */     for (int i = this.n - 1; i >= 0; i--) {
/* 153 */       for (int j = 0; j < this.m; j++) {
/* 154 */         arrayOfDouble[j][i] = 0.0D;
/*     */       }
/* 156 */       arrayOfDouble[i][i] = 1.0D;
/* 157 */       for (j = i; j < this.n; j++) {
/* 158 */         if (this.QR[i][i] != 0.0D) {
/* 159 */           double d = 0.0D;
/* 160 */           for (int k = i; k < this.m; k++) {
/* 161 */             d += this.QR[k][i] * arrayOfDouble[k][j];
/*     */           }
/* 163 */           d = -d / this.QR[i][i];
/* 164 */           for (k = i; k < this.m; k++) {
/* 165 */             arrayOfDouble[k][j] += d * this.QR[k][i];
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 170 */     return localMatrix;
/*     */   }
/*     */ 
/*     */   public Matrix solve(Matrix paramMatrix)
/*     */   {
/* 181 */     if (paramMatrix.getRowDimension() != this.m) {
/* 182 */       throw new IllegalArgumentException("Matrix row dimensions must agree.");
/*     */     }
/* 184 */     if (!isFullRank()) {
/* 185 */       throw new RuntimeException("Matrix is rank deficient.");
/*     */     }
/*     */ 
/* 189 */     int i = paramMatrix.getColumnDimension();
/* 190 */     double[][] arrayOfDouble = paramMatrix.getArrayCopy();
/*     */     int k;
/* 193 */     for (int j = 0; j < this.n; j++) {
/* 194 */       for (k = 0; k < i; k++) {
/* 195 */         double d = 0.0D;
/* 196 */         for (int i2 = j; i2 < this.m; i2++) {
/* 197 */           d += this.QR[i2][j] * arrayOfDouble[i2][k];
/*     */         }
/* 199 */         d = -d / this.QR[j][j];
/* 200 */         for (i2 = j; i2 < this.m; i2++) {
/* 201 */           arrayOfDouble[i2][k] += d * this.QR[i2][j];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 206 */     for (j = this.n - 1; j >= 0; j--) {
/* 207 */       for (k = 0; k < i; k++) {
/* 208 */         arrayOfDouble[j][k] /= this.Rdiag[j];
/*     */       }
/* 210 */       for (k = 0; k < j; k++) {
/* 211 */         for (int i1 = 0; i1 < i; i1++) {
/* 212 */           arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * this.QR[k][j];
/*     */         }
/*     */       }
/*     */     }
/* 216 */     return new Matrix(arrayOfDouble, this.n, i).getMatrix(0, this.n - 1, 0, i - 1);
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.QRDecomposition
 * JD-Core Version:    0.6.2
 */