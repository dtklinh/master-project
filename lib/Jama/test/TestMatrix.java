/*     */ package Jama.test;
/*     */ 
/*     */ import Jama.CholeskyDecomposition;
/*     */ import Jama.EigenvalueDecomposition;
/*     */ import Jama.LUDecomposition;
/*     */ import Jama.Matrix;
/*     */ import Jama.QRDecomposition;
/*     */ import Jama.SingularValueDecomposition;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TestMatrix
/*     */ {
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/*  35 */     int i = 0;
/*  36 */     int j = 0;
/*     */ 
/*  38 */     double[] arrayOfDouble1 = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D };
/*  39 */     double[] arrayOfDouble2 = { 1.0D, 4.0D, 7.0D, 10.0D, 2.0D, 5.0D, 8.0D, 11.0D, 3.0D, 6.0D, 9.0D, 12.0D };
/*  40 */     double[][] arrayOfDouble3 = { { 1.0D, 4.0D, 7.0D, 10.0D }, { 2.0D, 5.0D, 8.0D, 11.0D }, { 3.0D, 6.0D, 9.0D, 12.0D } };
/*  41 */     double[][] arrayOfDouble4 = arrayOfDouble3;
/*  42 */     double[][] arrayOfDouble5 = { { 1.0D, 2.0D, 3.0D }, { 4.0D, 5.0D, 6.0D }, { 7.0D, 8.0D, 9.0D }, { 10.0D, 11.0D, 12.0D } };
/*  43 */     double[][] arrayOfDouble6 = { { 5.0D, 8.0D, 11.0D }, { 6.0D, 9.0D, 12.0D } };
/*  44 */     double[][] arrayOfDouble7 = { { 1.0D, 4.0D, 7.0D }, { 2.0D, 5.0D, 8.0D, 11.0D }, { 3.0D, 6.0D, 9.0D, 12.0D } };
/*  45 */     double[][] arrayOfDouble8 = { { 4.0D, 1.0D, 1.0D }, { 1.0D, 2.0D, 3.0D }, { 1.0D, 3.0D, 6.0D } };
/*  46 */     double[][] arrayOfDouble9 = { { 1.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 1.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 1.0D, 0.0D } };
/*  47 */     double[][] arrayOfDouble10 = { { 0.0D, 1.0D, 0.0D, 0.0D }, { 1.0D, 0.0D, 2.0E-007D, 0.0D }, { 0.0D, -2.0E-007D, 0.0D, 1.0D }, { 0.0D, 0.0D, 1.0D, 0.0D } };
/*     */ 
/*  49 */     double[][] arrayOfDouble11 = { { 166.0D, 188.0D, 210.0D }, { 188.0D, 214.0D, 240.0D }, { 210.0D, 240.0D, 270.0D } };
/*  50 */     double[][] arrayOfDouble12 = { { 13.0D }, { 15.0D } };
/*  51 */     double[][] arrayOfDouble13 = { { 1.0D, 3.0D }, { 7.0D, 9.0D } };
/*  52 */     double[][] arrayOfDouble14 = { { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D }, { 0.0D, 0.0D, 0.0D, 1.0D, 0.0D }, { 1.0D, 1.0D, 0.0D, 0.0D, 1.0D }, { 1.0D, 0.0D, 1.0D, 0.0D, 1.0D } };
/*     */ 
/*  54 */     int k = 3; int m = 4;
/*  55 */     int n = 5;
/*  56 */     int i1 = 0;
/*  57 */     int i2 = 4;
/*  58 */     int i3 = 3;
/*  59 */     int i4 = 4;
/*  60 */     int i5 = 1; int i6 = 2; int i7 = 1; int i8 = 3;
/*  61 */     int[] arrayOfInt1 = { 1, 2 };
/*  62 */     int[] arrayOfInt2 = { 1, 3 };
/*  63 */     int[] arrayOfInt3 = { 1, 2, 3 };
/*  64 */     int[] arrayOfInt4 = { 1, 2, 4 };
/*  65 */     double d2 = 33.0D;
/*  66 */     double d3 = 30.0D;
/*  67 */     double d4 = 15.0D;
/*  68 */     double d5 = 650.0D;
/*     */ 
/*  82 */     print("\nTesting constructors and constructor-like methods...\n");
/*     */     try
/*     */     {
/*  85 */       localObject1 = new Matrix(arrayOfDouble1, n);
/*  86 */       i = try_failure(i, "Catch invalid length in packed constructor... ", "exception not thrown for invalid input");
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException1) {
/*  89 */       try_success("Catch invalid length in packed constructor... ", localIllegalArgumentException1.getMessage());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  95 */       localObject1 = new Matrix(arrayOfDouble7);
/*  96 */       d1 = ((Matrix)localObject1).get(i1, i2);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException2) {
/*  98 */       try_success("Catch ragged input to default constructor... ", localIllegalArgumentException2.getMessage());
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {
/* 101 */       i = try_failure(i, "Catch ragged input to constructor... ", "exception not thrown in construction...ArrayIndexOutOfBoundsException thrown later");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 107 */       localObject1 = Matrix.constructWithCopy(arrayOfDouble7);
/* 108 */       d1 = ((Matrix)localObject1).get(i1, i2);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException3) {
/* 110 */       try_success("Catch ragged input to constructWithCopy... ", localIllegalArgumentException3.getMessage());
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {
/* 112 */       i = try_failure(i, "Catch ragged input to constructWithCopy... ", "exception not thrown in construction...ArrayIndexOutOfBoundsException thrown later");
/*     */     }
/*     */ 
/* 115 */     Object localObject1 = new Matrix(arrayOfDouble1, i3);
/* 116 */     Matrix localMatrix1 = new Matrix(arrayOfDouble3);
/* 117 */     double d1 = localMatrix1.get(0, 0);
/* 118 */     arrayOfDouble3[0][0] = 0.0D;
/* 119 */     Matrix localMatrix2 = localMatrix1.minus((Matrix)localObject1);
/* 120 */     arrayOfDouble3[0][0] = d1;
/* 121 */     localMatrix1 = Matrix.constructWithCopy(arrayOfDouble3);
/* 122 */     d1 = localMatrix1.get(0, 0);
/* 123 */     arrayOfDouble3[0][0] = 0.0D;
/* 124 */     if (d1 - localMatrix1.get(0, 0) != 0.0D)
/*     */     {
/* 126 */       i = try_failure(i, "constructWithCopy... ", "copy not effected... data visible outside");
/*     */     }
/* 128 */     else try_success("constructWithCopy... ", "");
/*     */ 
/* 130 */     arrayOfDouble3[0][0] = arrayOfDouble1[0];
/* 131 */     Matrix localMatrix5 = new Matrix(arrayOfDouble9);
/*     */     try {
/* 133 */       check(localMatrix5, Matrix.identity(3, 4));
/* 134 */       try_success("identity... ", "");
/*     */     } catch (RuntimeException localRuntimeException1) {
/* 136 */       i = try_failure(i, "identity... ", "identity Matrix not successfully created");
/*     */     }
/*     */ 
/* 159 */     print("\nTesting access methods...\n");
/*     */ 
/* 165 */     localMatrix1 = new Matrix(arrayOfDouble3);
/* 166 */     if (localMatrix1.getRowDimension() != k)
/* 167 */       i = try_failure(i, "getRowDimension... ", "");
/*     */     else {
/* 169 */       try_success("getRowDimension... ", "");
/*     */     }
/* 171 */     if (localMatrix1.getColumnDimension() != m)
/* 172 */       i = try_failure(i, "getColumnDimension... ", "");
/*     */     else {
/* 174 */       try_success("getColumnDimension... ", "");
/*     */     }
/* 176 */     localMatrix1 = new Matrix(arrayOfDouble3);
/* 177 */     double[][] arrayOfDouble = localMatrix1.getArray();
/* 178 */     if (arrayOfDouble != arrayOfDouble3)
/* 179 */       i = try_failure(i, "getArray... ", "");
/*     */     else {
/* 181 */       try_success("getArray... ", "");
/*     */     }
/* 183 */     arrayOfDouble = localMatrix1.getArrayCopy();
/* 184 */     if (arrayOfDouble == arrayOfDouble3)
/* 185 */       i = try_failure(i, "getArrayCopy... ", "data not (deep) copied");
/*     */     try
/*     */     {
/* 188 */       check(arrayOfDouble, arrayOfDouble3);
/* 189 */       try_success("getArrayCopy... ", "");
/*     */     } catch (RuntimeException localRuntimeException2) {
/* 191 */       i = try_failure(i, "getArrayCopy... ", "data not successfully (deep) copied");
/*     */     }
/* 193 */     double[] arrayOfDouble15 = localMatrix1.getColumnPackedCopy();
/*     */     try {
/* 195 */       check(arrayOfDouble15, arrayOfDouble1);
/* 196 */       try_success("getColumnPackedCopy... ", "");
/*     */     } catch (RuntimeException localRuntimeException3) {
/* 198 */       i = try_failure(i, "getColumnPackedCopy... ", "data not successfully (deep) copied by columns");
/*     */     }
/* 200 */     arrayOfDouble15 = localMatrix1.getRowPackedCopy();
/*     */     try {
/* 202 */       check(arrayOfDouble15, arrayOfDouble2);
/* 203 */       try_success("getRowPackedCopy... ", "");
/*     */     } catch (RuntimeException localRuntimeException4) {
/* 205 */       i = try_failure(i, "getRowPackedCopy... ", "data not successfully (deep) copied by rows");
/*     */     }
/*     */     try {
/* 208 */       d1 = localMatrix1.get(localMatrix1.getRowDimension(), localMatrix1.getColumnDimension() - 1);
/* 209 */       i = try_failure(i, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException3) {
/*     */       try {
/* 212 */         d1 = localMatrix1.get(localMatrix1.getRowDimension() - 1, localMatrix1.getColumnDimension());
/* 213 */         i = try_failure(i, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException23) {
/* 215 */         try_success("get(int,int)... OutofBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException4) {
/* 218 */       i = try_failure(i, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 221 */       if (localMatrix1.get(localMatrix1.getRowDimension() - 1, localMatrix1.getColumnDimension() - 1) != arrayOfDouble3[(localMatrix1.getRowDimension() - 1)][(localMatrix1.getColumnDimension() - 1)])
/*     */       {
/* 223 */         i = try_failure(i, "get(int,int)... ", "Matrix entry (i,j) not successfully retreived");
/*     */       }
/* 225 */       else try_success("get(int,int)... ", ""); 
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException4)
/*     */     {
/* 228 */       i = try_failure(i, "get(int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/* 230 */     Matrix localMatrix9 = new Matrix(arrayOfDouble6);
/*     */     try {
/* 232 */       localMatrix10 = localMatrix1.getMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, i7, i8);
/* 233 */       i = try_failure(i, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException5) {
/*     */       try {
/* 236 */         localMatrix10 = localMatrix1.getMatrix(i5, i6, i7, i8 + localMatrix1.getColumnDimension() + 1);
/* 237 */         i = try_failure(i, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException24) {
/* 239 */         try_success("getMatrix(int,int,int,int)... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException5) {
/* 242 */       i = try_failure(i, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 245 */       localMatrix10 = localMatrix1.getMatrix(i5, i6, i7, i8);
/*     */       try {
/* 247 */         check(localMatrix9, localMatrix10);
/* 248 */         try_success("getMatrix(int,int,int,int)... ", "");
/*     */       } catch (RuntimeException localRuntimeException5) {
/* 250 */         i = try_failure(i, "getMatrix(int,int,int,int)... ", "submatrix not successfully retreived");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException6) {
/* 253 */       i = try_failure(i, "getMatrix(int,int,int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */     try
/*     */     {
/* 257 */       localMatrix10 = localMatrix1.getMatrix(i5, i6, arrayOfInt4);
/* 258 */       i = try_failure(i, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException7) {
/*     */       try {
/* 261 */         localMatrix10 = localMatrix1.getMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, arrayOfInt3);
/* 262 */         i = try_failure(i, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException25) {
/* 264 */         try_success("getMatrix(int,int,int[])... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException6) {
/* 267 */       i = try_failure(i, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 270 */       localMatrix10 = localMatrix1.getMatrix(i5, i6, arrayOfInt3);
/*     */       try {
/* 272 */         check(localMatrix9, localMatrix10);
/* 273 */         try_success("getMatrix(int,int,int[])... ", "");
/*     */       } catch (RuntimeException localRuntimeException6) {
/* 275 */         i = try_failure(i, "getMatrix(int,int,int[])... ", "submatrix not successfully retreived");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException8) {
/* 278 */       i = try_failure(i, "getMatrix(int,int,int[])... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */     try {
/* 281 */       localMatrix10 = localMatrix1.getMatrix(arrayOfInt2, i7, i8);
/* 282 */       i = try_failure(i, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException9) {
/*     */       try {
/* 285 */         localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, i7, i8 + localMatrix1.getColumnDimension() + 1);
/* 286 */         i = try_failure(i, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException26) {
/* 288 */         try_success("getMatrix(int[],int,int)... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException7) {
/* 291 */       i = try_failure(i, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 294 */       localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, i7, i8);
/*     */       try {
/* 296 */         check(localMatrix9, localMatrix10);
/* 297 */         try_success("getMatrix(int[],int,int)... ", "");
/*     */       } catch (RuntimeException localRuntimeException7) {
/* 299 */         i = try_failure(i, "getMatrix(int[],int,int)... ", "submatrix not successfully retreived");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException10) {
/* 302 */       i = try_failure(i, "getMatrix(int[],int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */     try {
/* 305 */       localMatrix10 = localMatrix1.getMatrix(arrayOfInt2, arrayOfInt3);
/* 306 */       i = try_failure(i, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException11) {
/*     */       try {
/* 309 */         localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, arrayOfInt4);
/* 310 */         i = try_failure(i, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException27) {
/* 312 */         try_success("getMatrix(int[],int[])... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException8) {
/* 315 */       i = try_failure(i, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 318 */       localMatrix10 = localMatrix1.getMatrix(arrayOfInt1, arrayOfInt3);
/*     */       try {
/* 320 */         check(localMatrix9, localMatrix10);
/* 321 */         try_success("getMatrix(int[],int[])... ", "");
/*     */       } catch (RuntimeException localRuntimeException8) {
/* 323 */         i = try_failure(i, "getMatrix(int[],int[])... ", "submatrix not successfully retreived");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException12) {
/* 326 */       i = try_failure(i, "getMatrix(int[],int[])... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 334 */       localMatrix1.set(localMatrix1.getRowDimension(), localMatrix1.getColumnDimension() - 1, 0.0D);
/* 335 */       i = try_failure(i, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException13) {
/*     */       try {
/* 338 */         localMatrix1.set(localMatrix1.getRowDimension() - 1, localMatrix1.getColumnDimension(), 0.0D);
/* 339 */         i = try_failure(i, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException28) {
/* 341 */         try_success("set(int,int,double)... OutofBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException9) {
/* 344 */       i = try_failure(i, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 347 */       localMatrix1.set(i5, i7, 0.0D);
/* 348 */       d1 = localMatrix1.get(i5, i7);
/*     */       try {
/* 350 */         check(d1, 0.0D);
/* 351 */         try_success("set(int,int,double)... ", "");
/*     */       } catch (RuntimeException localRuntimeException9) {
/* 353 */         i = try_failure(i, "set(int,int,double)... ", "Matrix element not successfully set");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException14) {
/* 356 */       i = try_failure(i, "set(int,int,double)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/* 358 */     Matrix localMatrix10 = new Matrix(2, 3, 0.0D);
/*     */     try {
/* 360 */       localMatrix1.setMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, i7, i8, localMatrix10);
/* 361 */       i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException15) {
/*     */       try {
/* 364 */         localMatrix1.setMatrix(i5, i6, i7, i8 + localMatrix1.getColumnDimension() + 1, localMatrix10);
/* 365 */         i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException29) {
/* 367 */         try_success("setMatrix(int,int,int,int,Matrix)... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException10) {
/* 370 */       i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 373 */       localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix10);
/*     */       try {
/* 375 */         check(localMatrix10.minus(localMatrix1.getMatrix(i5, i6, i7, i8)), localMatrix10);
/* 376 */         try_success("setMatrix(int,int,int,int,Matrix)... ", "");
/*     */       } catch (RuntimeException localRuntimeException10) {
/* 378 */         i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "submatrix not successfully set");
/*     */       }
/* 380 */       localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix9);
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException16) {
/* 382 */       i = try_failure(i, "setMatrix(int,int,int,int,Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */     try {
/* 385 */       localMatrix1.setMatrix(i5, i6 + localMatrix1.getRowDimension() + 1, arrayOfInt3, localMatrix10);
/* 386 */       i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException17) {
/*     */       try {
/* 389 */         localMatrix1.setMatrix(i5, i6, arrayOfInt4, localMatrix10);
/* 390 */         i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException30) {
/* 392 */         try_success("setMatrix(int,int,int[],Matrix)... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException11) {
/* 395 */       i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 398 */       localMatrix1.setMatrix(i5, i6, arrayOfInt3, localMatrix10);
/*     */       try {
/* 400 */         check(localMatrix10.minus(localMatrix1.getMatrix(i5, i6, arrayOfInt3)), localMatrix10);
/* 401 */         try_success("setMatrix(int,int,int[],Matrix)... ", "");
/*     */       } catch (RuntimeException localRuntimeException11) {
/* 403 */         i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "submatrix not successfully set");
/*     */       }
/* 405 */       localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix9);
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException18) {
/* 407 */       i = try_failure(i, "setMatrix(int,int,int[],Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */     try {
/* 410 */       localMatrix1.setMatrix(arrayOfInt1, i7, i8 + localMatrix1.getColumnDimension() + 1, localMatrix10);
/* 411 */       i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException19) {
/*     */       try {
/* 414 */         localMatrix1.setMatrix(arrayOfInt2, i7, i8, localMatrix10);
/* 415 */         i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException31) {
/* 417 */         try_success("setMatrix(int[],int,int,Matrix)... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException12) {
/* 420 */       i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 423 */       localMatrix1.setMatrix(arrayOfInt1, i7, i8, localMatrix10);
/*     */       try {
/* 425 */         check(localMatrix10.minus(localMatrix1.getMatrix(arrayOfInt1, i7, i8)), localMatrix10);
/* 426 */         try_success("setMatrix(int[],int,int,Matrix)... ", "");
/*     */       } catch (RuntimeException localRuntimeException12) {
/* 428 */         i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "submatrix not successfully set");
/*     */       }
/* 430 */       localMatrix1.setMatrix(i5, i6, i7, i8, localMatrix9);
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException20) {
/* 432 */       i = try_failure(i, "setMatrix(int[],int,int,Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */     try {
/* 435 */       localMatrix1.setMatrix(arrayOfInt1, arrayOfInt4, localMatrix10);
/* 436 */       i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException21) {
/*     */       try {
/* 439 */         localMatrix1.setMatrix(arrayOfInt2, arrayOfInt3, localMatrix10);
/* 440 */         i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException32) {
/* 442 */         try_success("setMatrix(int[],int[],Matrix)... ArrayIndexOutOfBoundsException... ", "");
/*     */       }
/*     */     } catch (IllegalArgumentException localIllegalArgumentException13) {
/* 445 */       i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
/*     */     }
/*     */     try {
/* 448 */       localMatrix1.setMatrix(arrayOfInt1, arrayOfInt3, localMatrix10);
/*     */       try {
/* 450 */         check(localMatrix10.minus(localMatrix1.getMatrix(arrayOfInt1, arrayOfInt3)), localMatrix10);
/* 451 */         try_success("setMatrix(int[],int[],Matrix)... ", "");
/*     */       } catch (RuntimeException localRuntimeException13) {
/* 453 */         i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "submatrix not successfully set");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException22) {
/* 456 */       i = try_failure(i, "setMatrix(int[],int[],Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
/*     */     }
/*     */ 
/* 474 */     print("\nTesting array-like methods...\n");
/* 475 */     Matrix localMatrix7 = new Matrix(arrayOfDouble1, i4);
/* 476 */     Matrix localMatrix6 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
/* 477 */     localObject1 = localMatrix6;
/*     */     try {
/* 479 */       localMatrix7 = ((Matrix)localObject1).minus(localMatrix7);
/* 480 */       i = try_failure(i, "minus conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException14) {
/* 482 */       try_success("minus conformance check... ", "");
/*     */     }
/* 484 */     if (((Matrix)localObject1).minus(localMatrix6).norm1() != 0.0D)
/* 485 */       i = try_failure(i, "minus... ", "(difference of identical Matrices is nonzero,\nSubsequent use of minus should be suspect)");
/*     */     else {
/* 487 */       try_success("minus... ", "");
/*     */     }
/* 489 */     localObject1 = localMatrix6.copy();
/* 490 */     ((Matrix)localObject1).minusEquals(localMatrix6);
/* 491 */     Matrix localMatrix3 = new Matrix(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
/*     */     try {
/* 493 */       ((Matrix)localObject1).minusEquals(localMatrix7);
/* 494 */       i = try_failure(i, "minusEquals conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException15) {
/* 496 */       try_success("minusEquals conformance check... ", "");
/*     */     }
/* 498 */     if (((Matrix)localObject1).minus(localMatrix3).norm1() != 0.0D)
/* 499 */       i = try_failure(i, "minusEquals... ", "(difference of identical Matrices is nonzero,\nSubsequent use of minus should be suspect)");
/*     */     else {
/* 501 */       try_success("minusEquals... ", "");
/*     */     }
/*     */ 
/* 504 */     localObject1 = localMatrix6.copy();
/* 505 */     localMatrix1 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
/* 506 */     localMatrix2 = ((Matrix)localObject1).minus(localMatrix1);
/*     */     try {
/* 508 */       localMatrix7 = ((Matrix)localObject1).plus(localMatrix7);
/* 509 */       i = try_failure(i, "plus conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException16) {
/* 511 */       try_success("plus conformance check... ", "");
/*     */     }
/*     */     try {
/* 514 */       check(localMatrix2.plus(localMatrix1), (Matrix)localObject1);
/* 515 */       try_success("plus... ", "");
/*     */     } catch (RuntimeException localRuntimeException14) {
/* 517 */       i = try_failure(i, "plus... ", "(C = A - B, but C + B != A)");
/*     */     }
/* 519 */     localMatrix2 = ((Matrix)localObject1).minus(localMatrix1);
/* 520 */     localMatrix2.plusEquals(localMatrix1);
/*     */     try {
/* 522 */       ((Matrix)localObject1).plusEquals(localMatrix7);
/* 523 */       i = try_failure(i, "plusEquals conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException17) {
/* 525 */       try_success("plusEquals conformance check... ", "");
/*     */     }
/*     */     try {
/* 528 */       check(localMatrix2, (Matrix)localObject1);
/* 529 */       try_success("plusEquals... ", "");
/*     */     } catch (RuntimeException localRuntimeException15) {
/* 531 */       i = try_failure(i, "plusEquals... ", "(C = A - B, but C = C + B != A)");
/*     */     }
/* 533 */     localObject1 = localMatrix6.uminus();
/*     */     try {
/* 535 */       check(((Matrix)localObject1).plus(localMatrix6), localMatrix3);
/* 536 */       try_success("uminus... ", "");
/*     */     } catch (RuntimeException localRuntimeException16) {
/* 538 */       i = try_failure(i, "uminus... ", "(-A + A != zeros)");
/*     */     }
/* 540 */     localObject1 = localMatrix6.copy();
/* 541 */     Matrix localMatrix4 = new Matrix(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension(), 1.0D);
/* 542 */     localMatrix2 = ((Matrix)localObject1).arrayLeftDivide(localMatrix6);
/*     */     try {
/* 544 */       localMatrix7 = ((Matrix)localObject1).arrayLeftDivide(localMatrix7);
/* 545 */       i = try_failure(i, "arrayLeftDivide conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException18) {
/* 547 */       try_success("arrayLeftDivide conformance check... ", "");
/*     */     }
/*     */     try {
/* 550 */       check(localMatrix2, localMatrix4);
/* 551 */       try_success("arrayLeftDivide... ", "");
/*     */     } catch (RuntimeException localRuntimeException17) {
/* 553 */       i = try_failure(i, "arrayLeftDivide... ", "(M.\\M != ones)");
/*     */     }
/*     */     try {
/* 556 */       ((Matrix)localObject1).arrayLeftDivideEquals(localMatrix7);
/* 557 */       i = try_failure(i, "arrayLeftDivideEquals conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException19) {
/* 559 */       try_success("arrayLeftDivideEquals conformance check... ", "");
/*     */     }
/* 561 */     ((Matrix)localObject1).arrayLeftDivideEquals(localMatrix6);
/*     */     try {
/* 563 */       check((Matrix)localObject1, localMatrix4);
/* 564 */       try_success("arrayLeftDivideEquals... ", "");
/*     */     } catch (RuntimeException localRuntimeException18) {
/* 566 */       i = try_failure(i, "arrayLeftDivideEquals... ", "(M.\\M != ones)");
/*     */     }
/* 568 */     localObject1 = localMatrix6.copy();
/*     */     try {
/* 570 */       ((Matrix)localObject1).arrayRightDivide(localMatrix7);
/* 571 */       i = try_failure(i, "arrayRightDivide conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException20) {
/* 573 */       try_success("arrayRightDivide conformance check... ", "");
/*     */     }
/* 575 */     localMatrix2 = ((Matrix)localObject1).arrayRightDivide(localMatrix6);
/*     */     try {
/* 577 */       check(localMatrix2, localMatrix4);
/* 578 */       try_success("arrayRightDivide... ", "");
/*     */     } catch (RuntimeException localRuntimeException19) {
/* 580 */       i = try_failure(i, "arrayRightDivide... ", "(M./M != ones)");
/*     */     }
/*     */     try {
/* 583 */       ((Matrix)localObject1).arrayRightDivideEquals(localMatrix7);
/* 584 */       i = try_failure(i, "arrayRightDivideEquals conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException21) {
/* 586 */       try_success("arrayRightDivideEquals conformance check... ", "");
/*     */     }
/* 588 */     ((Matrix)localObject1).arrayRightDivideEquals(localMatrix6);
/*     */     try {
/* 590 */       check((Matrix)localObject1, localMatrix4);
/* 591 */       try_success("arrayRightDivideEquals... ", "");
/*     */     } catch (RuntimeException localRuntimeException20) {
/* 593 */       i = try_failure(i, "arrayRightDivideEquals... ", "(M./M != ones)");
/*     */     }
/* 595 */     localObject1 = localMatrix6.copy();
/* 596 */     localMatrix1 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
/*     */     try {
/* 598 */       localMatrix7 = ((Matrix)localObject1).arrayTimes(localMatrix7);
/* 599 */       i = try_failure(i, "arrayTimes conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException22) {
/* 601 */       try_success("arrayTimes conformance check... ", "");
/*     */     }
/* 603 */     localMatrix2 = ((Matrix)localObject1).arrayTimes(localMatrix1);
/*     */     try {
/* 605 */       check(localMatrix2.arrayRightDivideEquals(localMatrix1), (Matrix)localObject1);
/* 606 */       try_success("arrayTimes... ", "");
/*     */     } catch (RuntimeException localRuntimeException21) {
/* 608 */       i = try_failure(i, "arrayTimes... ", "(A = R, C = A.*B, but C./B != A)");
/*     */     }
/*     */     try {
/* 611 */       ((Matrix)localObject1).arrayTimesEquals(localMatrix7);
/* 612 */       i = try_failure(i, "arrayTimesEquals conformance check... ", "nonconformance not raised");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException23) {
/* 614 */       try_success("arrayTimesEquals conformance check... ", "");
/*     */     }
/* 616 */     ((Matrix)localObject1).arrayTimesEquals(localMatrix1);
/*     */     try {
/* 618 */       check(((Matrix)localObject1).arrayRightDivideEquals(localMatrix1), localMatrix6);
/* 619 */       try_success("arrayTimesEquals... ", "");
/*     */     } catch (RuntimeException localRuntimeException22) {
/* 621 */       i = try_failure(i, "arrayTimesEquals... ", "(A = R, A = A.*B, but A./B != R)");
/*     */     }
/*     */ 
/* 632 */     print("\nTesting I/O methods...\n");
/*     */     Object localObject3;
/*     */     try
/*     */     {
/* 634 */       DecimalFormat localDecimalFormat = new DecimalFormat("0.0000E00");
/* 635 */       localDecimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
/*     */ 
/* 637 */       localObject2 = new PrintWriter(new FileOutputStream("JamaTestMatrix.out"));
/* 638 */       ((Matrix)localObject1).print((PrintWriter)localObject2, localDecimalFormat, 10);
/* 639 */       ((PrintWriter)localObject2).close();
/* 640 */       localMatrix6 = Matrix.read(new BufferedReader(new FileReader("JamaTestMatrix.out")));
/* 641 */       if (((Matrix)localObject1).minus(localMatrix6).norm1() < 0.001D)
/* 642 */         try_success("print()/read()...", "");
/*     */       else
/* 644 */         i = try_failure(i, "print()/read()...", "Matrix read from file does not match Matrix printed to file");
/*     */     }
/*     */     catch (IOException localIOException1) {
/* 647 */       j = try_warning(j, "print()/read()...", "unexpected I/O error, unable to run print/read test;  check write permission in current directory and retry");
/*     */     } catch (Exception localException1) {
/*     */       try {
/* 650 */         localException1.printStackTrace(System.out);
/* 651 */         j = try_warning(j, "print()/read()...", "Formatting error... will try JDK1.1 reformulation...");
/* 652 */         Object localObject2 = new DecimalFormat("0.0000");
/* 653 */         localObject3 = new PrintWriter(new FileOutputStream("JamaTestMatrix.out"));
/* 654 */         ((Matrix)localObject1).print((PrintWriter)localObject3, (NumberFormat)localObject2, 10);
/* 655 */         ((PrintWriter)localObject3).close();
/* 656 */         localMatrix6 = Matrix.read(new BufferedReader(new FileReader("JamaTestMatrix.out")));
/* 657 */         if (((Matrix)localObject1).minus(localMatrix6).norm1() < 0.001D)
/* 658 */           try_success("print()/read()...", "");
/*     */         else
/* 660 */           i = try_failure(i, "print()/read() (2nd attempt) ...", "Matrix read from file does not match Matrix printed to file");
/*     */       }
/*     */       catch (IOException localIOException2) {
/* 663 */         j = try_warning(j, "print()/read()...", "unexpected I/O error, unable to run print/read test;  check write permission in current directory and retry");
/*     */       }
/*     */     }
/*     */ 
/* 667 */     localMatrix6 = Matrix.random(((Matrix)localObject1).getRowDimension(), ((Matrix)localObject1).getColumnDimension());
/* 668 */     String str = "TMPMATRIX.serial";
/*     */     try {
/* 670 */       ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(str));
/* 671 */       localObjectOutputStream.writeObject(localMatrix6);
/* 672 */       localObject3 = new ObjectInputStream(new FileInputStream(str));
/* 673 */       localObject1 = (Matrix)((ObjectInputStream)localObject3).readObject();
/*     */       try
/*     */       {
/* 676 */         check((Matrix)localObject1, localMatrix6);
/* 677 */         try_success("writeObject(Matrix)/readObject(Matrix)...", "");
/*     */       } catch (RuntimeException localRuntimeException32) {
/* 679 */         i = try_failure(i, "writeObject(Matrix)/readObject(Matrix)...", "Matrix not serialized correctly");
/*     */       }
/*     */     } catch (IOException localIOException3) {
/* 682 */       j = try_warning(j, "writeObject()/readObject()...", "unexpected I/O error, unable to run serialization test;  check write permission in current directory and retry");
/*     */     } catch (Exception localException2) {
/* 684 */       i = try_failure(i, "writeObject(Matrix)/readObject(Matrix)...", "unexpected error in serialization test");
/*     */     }
/*     */ 
/* 709 */     print("\nTesting linear algebra methods...\n");
/* 710 */     localObject1 = new Matrix(arrayOfDouble1, 3);
/* 711 */     Matrix localMatrix11 = new Matrix(arrayOfDouble5);
/* 712 */     localMatrix11 = ((Matrix)localObject1).transpose();
/*     */     try {
/* 714 */       check(((Matrix)localObject1).transpose(), localMatrix11);
/* 715 */       try_success("transpose...", "");
/*     */     } catch (RuntimeException localRuntimeException23) {
/* 717 */       i = try_failure(i, "transpose()...", "transpose unsuccessful");
/*     */     }
/* 719 */     ((Matrix)localObject1).transpose();
/*     */     try {
/* 721 */       check(((Matrix)localObject1).norm1(), d2);
/* 722 */       try_success("norm1...", "");
/*     */     } catch (RuntimeException localRuntimeException24) {
/* 724 */       i = try_failure(i, "norm1()...", "incorrect norm calculation");
/*     */     }
/*     */     try {
/* 727 */       check(((Matrix)localObject1).normInf(), d3);
/* 728 */       try_success("normInf()...", "");
/*     */     } catch (RuntimeException localRuntimeException25) {
/* 730 */       i = try_failure(i, "normInf()...", "incorrect norm calculation");
/*     */     }
/*     */     try {
/* 733 */       check(((Matrix)localObject1).normF(), Math.sqrt(d5));
/* 734 */       try_success("normF...", "");
/*     */     } catch (RuntimeException localRuntimeException26) {
/* 736 */       i = try_failure(i, "normF()...", "incorrect norm calculation");
/*     */     }
/*     */     try {
/* 739 */       check(((Matrix)localObject1).trace(), d4);
/* 740 */       try_success("trace()...", "");
/*     */     } catch (RuntimeException localRuntimeException27) {
/* 742 */       i = try_failure(i, "trace()...", "incorrect trace calculation");
/*     */     }
/*     */     try {
/* 745 */       check(((Matrix)localObject1).getMatrix(0, ((Matrix)localObject1).getRowDimension() - 1, 0, ((Matrix)localObject1).getRowDimension() - 1).det(), 0.0D);
/* 746 */       try_success("det()...", "");
/*     */     } catch (RuntimeException localRuntimeException28) {
/* 748 */       i = try_failure(i, "det()...", "incorrect determinant calculation");
/*     */     }
/* 750 */     Matrix localMatrix12 = new Matrix(arrayOfDouble11);
/*     */     try {
/* 752 */       check(((Matrix)localObject1).times(((Matrix)localObject1).transpose()), localMatrix12);
/* 753 */       try_success("times(Matrix)...", "");
/*     */     } catch (RuntimeException localRuntimeException29) {
/* 755 */       i = try_failure(i, "times(Matrix)...", "incorrect Matrix-Matrix product calculation");
/*     */     }
/*     */     try {
/* 758 */       check(((Matrix)localObject1).times(0.0D), localMatrix3);
/* 759 */       try_success("times(double)...", "");
/*     */     } catch (RuntimeException localRuntimeException30) {
/* 761 */       i = try_failure(i, "times(double)...", "incorrect Matrix-scalar product calculation");
/*     */     }
/*     */ 
/* 764 */     localObject1 = new Matrix(arrayOfDouble1, 4);
/* 765 */     QRDecomposition localQRDecomposition = ((Matrix)localObject1).qr();
/* 766 */     localMatrix6 = localQRDecomposition.getR();
/*     */     try {
/* 768 */       check((Matrix)localObject1, localQRDecomposition.getQ().times(localMatrix6));
/* 769 */       try_success("QRDecomposition...", "");
/*     */     } catch (RuntimeException localRuntimeException31) {
/* 771 */       i = try_failure(i, "QRDecomposition...", "incorrect QR decomposition calculation");
/*     */     }
/* 773 */     SingularValueDecomposition localSingularValueDecomposition = ((Matrix)localObject1).svd();
/*     */     try {
/* 775 */       check((Matrix)localObject1, localSingularValueDecomposition.getU().times(localSingularValueDecomposition.getS().times(localSingularValueDecomposition.getV().transpose())));
/* 776 */       try_success("SingularValueDecomposition...", "");
/*     */     } catch (RuntimeException localRuntimeException33) {
/* 778 */       i = try_failure(i, "SingularValueDecomposition...", "incorrect singular value decomposition calculation");
/*     */     }
/* 780 */     Matrix localMatrix13 = new Matrix(arrayOfDouble4);
/*     */     try {
/* 782 */       check(localMatrix13.rank(), Math.min(localMatrix13.getRowDimension(), localMatrix13.getColumnDimension()) - 1);
/* 783 */       try_success("rank()...", "");
/*     */     } catch (RuntimeException localRuntimeException34) {
/* 785 */       i = try_failure(i, "rank()...", "incorrect rank calculation");
/*     */     }
/* 787 */     localMatrix1 = new Matrix(arrayOfDouble13);
/* 788 */     localSingularValueDecomposition = localMatrix1.svd();
/* 789 */     double[] arrayOfDouble16 = localSingularValueDecomposition.getSingularValues();
/*     */     try {
/* 791 */       check(localMatrix1.cond(), arrayOfDouble16[0] / arrayOfDouble16[(Math.min(localMatrix1.getRowDimension(), localMatrix1.getColumnDimension()) - 1)]);
/* 792 */       try_success("cond()...", "");
/*     */     } catch (RuntimeException localRuntimeException35) {
/* 794 */       i = try_failure(i, "cond()...", "incorrect condition number calculation");
/*     */     }
/* 796 */     int i9 = ((Matrix)localObject1).getColumnDimension();
/* 797 */     localObject1 = ((Matrix)localObject1).getMatrix(0, i9 - 1, 0, i9 - 1);
/* 798 */     ((Matrix)localObject1).set(0, 0, 0.0D);
/* 799 */     LUDecomposition localLUDecomposition = ((Matrix)localObject1).lu();
/*     */     try {
/* 801 */       check(((Matrix)localObject1).getMatrix(localLUDecomposition.getPivot(), 0, i9 - 1), localLUDecomposition.getL().times(localLUDecomposition.getU()));
/* 802 */       try_success("LUDecomposition...", "");
/*     */     } catch (RuntimeException localRuntimeException36) {
/* 804 */       i = try_failure(i, "LUDecomposition...", "incorrect LU decomposition calculation");
/*     */     }
/* 806 */     Matrix localMatrix8 = ((Matrix)localObject1).inverse();
/*     */     try {
/* 808 */       check(((Matrix)localObject1).times(localMatrix8), Matrix.identity(3, 3));
/* 809 */       try_success("inverse()...", "");
/*     */     } catch (RuntimeException localRuntimeException37) {
/* 811 */       i = try_failure(i, "inverse()...", "incorrect inverse calculation");
/*     */     }
/* 813 */     localMatrix4 = new Matrix(localMatrix9.getRowDimension(), 1, 1.0D);
/* 814 */     Matrix localMatrix14 = new Matrix(arrayOfDouble12);
/* 815 */     localMatrix12 = localMatrix9.getMatrix(0, localMatrix9.getRowDimension() - 1, 0, localMatrix9.getRowDimension() - 1);
/*     */     try {
/* 817 */       check(localMatrix12.solve(localMatrix14), localMatrix4);
/* 818 */       try_success("solve()...", "");
/*     */     } catch (IllegalArgumentException localIllegalArgumentException24) {
/* 820 */       i = try_failure(i, "solve()...", localIllegalArgumentException24.getMessage());
/*     */     } catch (RuntimeException localRuntimeException38) {
/* 822 */       i = try_failure(i, "solve()...", localRuntimeException38.getMessage());
/*     */     }
/* 824 */     localObject1 = new Matrix(arrayOfDouble8);
/* 825 */     CholeskyDecomposition localCholeskyDecomposition = ((Matrix)localObject1).chol();
/* 826 */     Matrix localMatrix15 = localCholeskyDecomposition.getL();
/*     */     try {
/* 828 */       check((Matrix)localObject1, localMatrix15.times(localMatrix15.transpose()));
/* 829 */       try_success("CholeskyDecomposition...", "");
/*     */     } catch (RuntimeException localRuntimeException39) {
/* 831 */       i = try_failure(i, "CholeskyDecomposition...", "incorrect Cholesky decomposition calculation");
/*     */     }
/* 833 */     localMatrix8 = localCholeskyDecomposition.solve(Matrix.identity(3, 3));
/*     */     try {
/* 835 */       check(((Matrix)localObject1).times(localMatrix8), Matrix.identity(3, 3));
/* 836 */       try_success("CholeskyDecomposition solve()...", "");
/*     */     } catch (RuntimeException localRuntimeException40) {
/* 838 */       i = try_failure(i, "CholeskyDecomposition solve()...", "incorrect Choleskydecomposition solve calculation");
/*     */     }
/* 840 */     EigenvalueDecomposition localEigenvalueDecomposition1 = ((Matrix)localObject1).eig();
/* 841 */     Matrix localMatrix16 = localEigenvalueDecomposition1.getD();
/* 842 */     Matrix localMatrix17 = localEigenvalueDecomposition1.getV();
/*     */     try {
/* 844 */       check(((Matrix)localObject1).times(localMatrix17), localMatrix17.times(localMatrix16));
/* 845 */       try_success("EigenvalueDecomposition (symmetric)...", "");
/*     */     } catch (RuntimeException localRuntimeException41) {
/* 847 */       i = try_failure(i, "EigenvalueDecomposition (symmetric)...", "incorrect symmetric Eigenvalue decomposition calculation");
/*     */     }
/* 849 */     localObject1 = new Matrix(arrayOfDouble10);
/* 850 */     localEigenvalueDecomposition1 = ((Matrix)localObject1).eig();
/* 851 */     localMatrix16 = localEigenvalueDecomposition1.getD();
/* 852 */     localMatrix17 = localEigenvalueDecomposition1.getV();
/*     */     try {
/* 854 */       check(((Matrix)localObject1).times(localMatrix17), localMatrix17.times(localMatrix16));
/* 855 */       try_success("EigenvalueDecomposition (nonsymmetric)...", "");
/*     */     } catch (RuntimeException localRuntimeException42) {
/* 857 */       i = try_failure(i, "EigenvalueDecomposition (nonsymmetric)...", "incorrect nonsymmetric Eigenvalue decomposition calculation");
/*     */     }
/*     */     try
/*     */     {
/* 861 */       print("\nTesting Eigenvalue; If this hangs, we've failed\n");
/* 862 */       Matrix localMatrix18 = new Matrix(arrayOfDouble14);
/* 863 */       EigenvalueDecomposition localEigenvalueDecomposition2 = localMatrix18.eig();
/* 864 */       try_success("EigenvalueDecomposition (hang)...", "");
/*     */     } catch (RuntimeException localRuntimeException43) {
/* 866 */       i = try_failure(i, "EigenvalueDecomposition (hang)...", "incorrect termination");
/*     */     }
/*     */ 
/* 871 */     print("\nTestMatrix completed.\n");
/* 872 */     print("Total errors reported: " + Integer.toString(i) + "\n");
/* 873 */     print("Total warnings reported: " + Integer.toString(j) + "\n");
/*     */   }
/*     */ 
/*     */   private static void check(double paramDouble1, double paramDouble2)
/*     */   {
/* 881 */     double d = Math.pow(2.0D, -52.0D);
/* 882 */     if (((paramDouble1 == 0.0D ? 1 : 0) & (Math.abs(paramDouble2) < 10.0D * d ? 1 : 0)) != 0) return;
/* 883 */     if (((paramDouble2 == 0.0D ? 1 : 0) & (Math.abs(paramDouble1) < 10.0D * d ? 1 : 0)) != 0) return;
/* 884 */     if (Math.abs(paramDouble1 - paramDouble2) > 10.0D * d * Math.max(Math.abs(paramDouble1), Math.abs(paramDouble2)))
/* 885 */       throw new RuntimeException("The difference x-y is too large: x = " + Double.toString(paramDouble1) + "  y = " + Double.toString(paramDouble2));
/*     */   }
/*     */ 
/*     */   private static void check(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*     */   {
/* 892 */     if (paramArrayOfDouble1.length == paramArrayOfDouble2.length) {
/* 893 */       for (int i = 0; i < paramArrayOfDouble1.length; i++)
/* 894 */         check(paramArrayOfDouble1[i], paramArrayOfDouble2[i]);
/*     */     }
/*     */     else
/* 897 */       throw new RuntimeException("Attempt to compare vectors of different lengths");
/*     */   }
/*     */ 
/*     */   private static void check(double[][] paramArrayOfDouble1, double[][] paramArrayOfDouble2)
/*     */   {
/* 904 */     Matrix localMatrix1 = new Matrix(paramArrayOfDouble1);
/* 905 */     Matrix localMatrix2 = new Matrix(paramArrayOfDouble2);
/* 906 */     check(localMatrix1, localMatrix2);
/*     */   }
/*     */ 
/*     */   private static void check(Matrix paramMatrix1, Matrix paramMatrix2)
/*     */   {
/* 912 */     double d = Math.pow(2.0D, -52.0D);
/* 913 */     if (((paramMatrix1.norm1() == 0.0D ? 1 : 0) & (paramMatrix2.norm1() < 10.0D * d ? 1 : 0)) != 0) return;
/* 914 */     if (((paramMatrix2.norm1() == 0.0D ? 1 : 0) & (paramMatrix1.norm1() < 10.0D * d ? 1 : 0)) != 0) return;
/* 915 */     if (paramMatrix1.minus(paramMatrix2).norm1() > 1000.0D * d * Math.max(paramMatrix1.norm1(), paramMatrix2.norm1()))
/* 916 */       throw new RuntimeException("The norm of (X-Y) is too large: " + Double.toString(paramMatrix1.minus(paramMatrix2).norm1()));
/*     */   }
/*     */ 
/*     */   private static void print(String paramString)
/*     */   {
/* 923 */     System.out.print(paramString);
/*     */   }
/*     */ 
/*     */   private static void try_success(String paramString1, String paramString2)
/*     */   {
/* 929 */     print(">    " + paramString1 + "success\n");
/* 930 */     if (paramString2 != "")
/* 931 */       print(">      Message: " + paramString2 + "\n");
/*     */   }
/*     */ 
/*     */   private static int try_failure(int paramInt, String paramString1, String paramString2)
/*     */   {
/* 937 */     print(">    " + paramString1 + "*** failure ***\n>      Message: " + paramString2 + "\n");
/* 938 */     paramInt++; return paramInt;
/*     */   }
/*     */ 
/*     */   private static int try_warning(int paramInt, String paramString1, String paramString2)
/*     */   {
/* 944 */     print(">    " + paramString1 + "*** warning ***\n>      Message: " + paramString2 + "\n");
/* 945 */     paramInt++; return paramInt;
/*     */   }
/*     */ 
/*     */   private static void print(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*     */   {
/* 952 */     System.out.print("\n");
/* 953 */     new Matrix(paramArrayOfDouble, 1).print(paramInt1, paramInt2);
/* 954 */     print("\n");
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.test.TestMatrix
 * JD-Core Version:    0.6.2
 */