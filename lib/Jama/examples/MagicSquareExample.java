/*     */ package Jama.examples;
/*     */ 
/*     */ import Jama.EigenvalueDecomposition;
/*     */ import Jama.LUDecomposition;
/*     */ import Jama.Matrix;
/*     */ import Jama.QRDecomposition;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class MagicSquareExample
/*     */ {
/*     */   public static Matrix magic(int paramInt)
/*     */   {
/*  13 */     double[][] arrayOfDouble = new double[paramInt][paramInt];
/*     */     int i;
/*     */     int j;
/*     */     int m;
/*  17 */     if (paramInt % 2 == 1) {
/*  18 */       i = (paramInt + 1) / 2;
/*  19 */       j = paramInt + 1;
/*  20 */       for (int k = 0; k < paramInt; k++) {
/*  21 */         for (m = 0; m < paramInt; m++) {
/*  22 */           arrayOfDouble[m][k] = (paramInt * ((m + k + i) % paramInt) + (m + 2 * k + j) % paramInt + 1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*  28 */     else if (paramInt % 4 == 0) {
/*  29 */       for (i = 0; i < paramInt; i++) {
/*  30 */         for (j = 0; j < paramInt; j++) {
/*  31 */           if ((j + 1) / 2 % 2 == (i + 1) / 2 % 2)
/*  32 */             arrayOfDouble[j][i] = (paramInt * paramInt - paramInt * j - i);
/*     */           else {
/*  34 */             arrayOfDouble[j][i] = (paramInt * j + i + 1);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*  42 */       i = paramInt / 2;
/*  43 */       j = (paramInt - 2) / 4;
/*  44 */       Matrix localMatrix = magic(i);
/*     */       int n;
/*     */       double d2;
/*  45 */       for (m = 0; m < i; m++) {
/*  46 */         for (n = 0; n < i; n++) {
/*  47 */           d2 = localMatrix.get(n, m);
/*  48 */           arrayOfDouble[n][m] = d2;
/*  49 */           arrayOfDouble[n][(m + i)] = (d2 + 2 * i * i);
/*  50 */           arrayOfDouble[(n + i)][m] = (d2 + 3 * i * i);
/*  51 */           arrayOfDouble[(n + i)][(m + i)] = (d2 + i * i);
/*     */         }
/*     */       }
/*  54 */       for (m = 0; m < i; m++) {
/*  55 */         for (n = 0; n < j; n++) {
/*  56 */           d2 = arrayOfDouble[m][n]; arrayOfDouble[m][n] = arrayOfDouble[(m + i)][n]; arrayOfDouble[(m + i)][n] = d2;
/*     */         }
/*  58 */         for (n = paramInt - j + 1; n < paramInt; n++) {
/*  59 */           d2 = arrayOfDouble[m][n]; arrayOfDouble[m][n] = arrayOfDouble[(m + i)][n]; arrayOfDouble[(m + i)][n] = d2;
/*     */         }
/*     */       }
/*  62 */       double d1 = arrayOfDouble[j][0]; arrayOfDouble[j][0] = arrayOfDouble[(j + i)][0]; arrayOfDouble[(j + i)][0] = d1;
/*  63 */       d1 = arrayOfDouble[j][j]; arrayOfDouble[j][j] = arrayOfDouble[(j + i)][j]; arrayOfDouble[(j + i)][j] = d1;
/*     */     }
/*  65 */     return new Matrix(arrayOfDouble);
/*     */   }
/*     */ 
/*     */   private static void print(String paramString)
/*     */   {
/*  71 */     System.out.print(paramString);
/*     */   }
/*     */ 
/*     */   public static String fixedWidthDoubletoString(double paramDouble, int paramInt1, int paramInt2)
/*     */   {
/*  77 */     DecimalFormat localDecimalFormat = new DecimalFormat();
/*  78 */     localDecimalFormat.setMaximumFractionDigits(paramInt2);
/*  79 */     localDecimalFormat.setMinimumFractionDigits(paramInt2);
/*  80 */     localDecimalFormat.setGroupingUsed(false);
/*  81 */     String str = localDecimalFormat.format(paramDouble);
/*  82 */     while (str.length() < paramInt1) {
/*  83 */       str = " " + str;
/*     */     }
/*  85 */     return str;
/*     */   }
/*     */ 
/*     */   public static String fixedWidthIntegertoString(int paramInt1, int paramInt2)
/*     */   {
/*  91 */     String str = Integer.toString(paramInt1);
/*  92 */     while (str.length() < paramInt2) {
/*  93 */       str = " " + str;
/*     */     }
/*  95 */     return str;
/*     */   }
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/* 114 */     print("\n    Test of Matrix Class, using magic squares.\n");
/* 115 */     print("    See MagicSquareExample.main() for an explanation.\n");
/* 116 */     print("\n      n     trace       max_eig   rank        cond      lu_res      qr_res\n\n");
/*     */ 
/* 118 */     Date localDate1 = new Date();
/* 119 */     double d1 = Math.pow(2.0D, -52.0D);
/* 120 */     for (int i = 3; i <= 32; i++) {
/* 121 */       print(fixedWidthIntegertoString(i, 7));
/*     */ 
/* 123 */       Matrix localMatrix1 = magic(i);
/*     */ 
/* 125 */       int j = (int)localMatrix1.trace();
/* 126 */       print(fixedWidthIntegertoString(j, 10));
/*     */ 
/* 128 */       EigenvalueDecomposition localEigenvalueDecomposition = new EigenvalueDecomposition(localMatrix1.plus(localMatrix1.transpose()).times(0.5D));
/*     */ 
/* 130 */       double[] arrayOfDouble = localEigenvalueDecomposition.getRealEigenvalues();
/* 131 */       print(fixedWidthDoubletoString(arrayOfDouble[(i - 1)], 14, 3));
/*     */ 
/* 133 */       int k = localMatrix1.rank();
/* 134 */       print(fixedWidthIntegertoString(k, 7));
/*     */ 
/* 136 */       double d3 = localMatrix1.cond();
/* 137 */       print(d3 < 1.0D / d1 ? fixedWidthDoubletoString(d3, 12, 3) : "         Inf");
/*     */ 
/* 140 */       LUDecomposition localLUDecomposition = new LUDecomposition(localMatrix1);
/* 141 */       Matrix localMatrix2 = localLUDecomposition.getL();
/* 142 */       Matrix localMatrix3 = localLUDecomposition.getU();
/* 143 */       int[] arrayOfInt = localLUDecomposition.getPivot();
/* 144 */       Matrix localMatrix4 = localMatrix2.times(localMatrix3).minus(localMatrix1.getMatrix(arrayOfInt, 0, i - 1));
/* 145 */       double d4 = localMatrix4.norm1() / (i * d1);
/* 146 */       print(fixedWidthDoubletoString(d4, 12, 3));
/*     */ 
/* 148 */       QRDecomposition localQRDecomposition = new QRDecomposition(localMatrix1);
/* 149 */       Matrix localMatrix5 = localQRDecomposition.getQ();
/* 150 */       localMatrix4 = localQRDecomposition.getR();
/* 151 */       localMatrix4 = localMatrix5.times(localMatrix4).minus(localMatrix1);
/* 152 */       d4 = localMatrix4.norm1() / (i * d1);
/* 153 */       print(fixedWidthDoubletoString(d4, 12, 3));
/*     */ 
/* 155 */       print("\n");
/*     */     }
/* 157 */     Date localDate2 = new Date();
/* 158 */     double d2 = (localDate2.getTime() - localDate1.getTime()) / 1000.0D;
/* 159 */     print("\nElapsed Time = " + fixedWidthDoubletoString(d2, 12, 3) + " seconds\n");
/*     */ 
/* 161 */     print("Adios\n");
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.examples.MagicSquareExample
 * JD-Core Version:    0.6.2
 */