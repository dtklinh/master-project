/*     */ package Jama;
/*     */ 
/*     */ import Jama.util.Maths;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SingularValueDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] U;
/*     */   private double[][] V;
/*     */   private double[] s;
/*     */   private int m;
/*     */   private int n;
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public SingularValueDecomposition(Matrix paramMatrix)
/*     */   {
/*  54 */     double[][] arrayOfDouble = paramMatrix.getArrayCopy();
/*  55 */     this.m = paramMatrix.getRowDimension();
/*  56 */     this.n = paramMatrix.getColumnDimension();
/*     */ 
/*  63 */     int i = Math.min(this.m, this.n);
/*  64 */     this.s = new double[Math.min(this.m + 1, this.n)];
/*  65 */     this.U = new double[this.m][i];
/*  66 */     this.V = new double[this.n][this.n];
/*  67 */     double[] arrayOfDouble1 = new double[this.n];
/*  68 */     double[] arrayOfDouble2 = new double[this.m];
/*  69 */     int j = 1;
/*  70 */     int k = 1;
/*     */ 
/*  75 */     int i1 = Math.min(this.m - 1, this.n);
/*  76 */     int i2 = Math.max(0, Math.min(this.n - 2, this.m));
/*  77 */     for (int i3 = 0; i3 < Math.max(i1, i2); i3++) {
/*  78 */       if (i3 < i1)
/*     */       {
/*  83 */         this.s[i3] = 0.0D;
/*  84 */         for (int i4 = i3; i4 < this.m; i4++) {
/*  85 */           this.s[i3] = Maths.hypot(this.s[i3], arrayOfDouble[i4][i3]);
/*     */         }
/*  87 */         if (this.s[i3] != 0.0D) {
/*  88 */           if (arrayOfDouble[i3][i3] < 0.0D) {
/*  89 */             this.s[i3] = (-this.s[i3]);
/*     */           }
/*  91 */           for (int i4 = i3; i4 < this.m; i4++) {
/*  92 */             arrayOfDouble[i4][i3] /= this.s[i3];
/*     */           }
/*  94 */           arrayOfDouble[i3][i3] += 1.0D;
/*     */         }
/*  96 */         this.s[i3] = (-this.s[i3]);
/*     */       }
/*     */       int i7;
/*  98 */       for (int i4 = i3 + 1; i4 < this.n; i4++) {
/*  99 */         if (((i3 < i1 ? 1 : 0) & (this.s[i3] != 0.0D ? 1 : 0)) != 0)
/*     */         {
/* 103 */           double d1 = 0.0D;
/* 104 */           for (i7 = i3; i7 < this.m; i7++) {
/* 105 */             d1 += arrayOfDouble[i7][i3] * arrayOfDouble[i7][i4];
/*     */           }
/* 107 */           d1 = -d1 / arrayOfDouble[i3][i3];
/* 108 */           for (i7 = i3; i7 < this.m; i7++) {
/* 109 */             arrayOfDouble[i7][i4] += d1 * arrayOfDouble[i7][i3];
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 116 */         arrayOfDouble1[i4] = arrayOfDouble[i3][i4];
/*     */       }
/* 118 */       if ((j & (i3 < i1 ? 1 : 0)) != 0)
/*     */       {
/* 123 */         for (int i4 = i3; i4 < this.m; i4++) {
/* 124 */           this.U[i4][i3] = arrayOfDouble[i4][i3];
/*     */         }
/*     */       }
/* 127 */       if (i3 < i2)
/*     */       {
/* 132 */         arrayOfDouble1[i3] = 0.0D;
/* 133 */         for (int i4 = i3 + 1; i4 < this.n; i4++) {
/* 134 */           arrayOfDouble1[i3] = Maths.hypot(arrayOfDouble1[i3], arrayOfDouble1[i4]);
/*     */         }
/* 136 */         if (arrayOfDouble1[i3] != 0.0D) {
/* 137 */           if (arrayOfDouble1[(i3 + 1)] < 0.0D) {
/* 138 */             arrayOfDouble1[i3] = (-arrayOfDouble1[i3]);
/*     */           }
/* 140 */           for (int i4 = i3 + 1; i4 < this.n; i4++) {
/* 141 */             arrayOfDouble1[i4] /= arrayOfDouble1[i3];
/*     */           }
/* 143 */           arrayOfDouble1[(i3 + 1)] += 1.0D;
/*     */         }
/* 145 */         arrayOfDouble1[i3] = (-arrayOfDouble1[i3]);
/* 146 */         if (((i3 + 1 < this.m ? 1 : 0) & (arrayOfDouble1[i3] != 0.0D ? 1 : 0)) != 0)
/*     */         {
/* 150 */           for (int i4 = i3 + 1; i4 < this.m; i4++) {
/* 151 */             arrayOfDouble2[i4] = 0.0D;
/*     */           }
/* 153 */           for (int i4 = i3 + 1; i4 < this.n; i4++) {
/* 154 */             for (int i5 = i3 + 1; i5 < this.m; i5++) {
/* 155 */               arrayOfDouble2[i5] += arrayOfDouble1[i4] * arrayOfDouble[i5][i4];
/*     */             }
/*     */           }
/* 158 */           for (int i4 = i3 + 1; i4 < this.n; i4++) {
/* 159 */             double d2 = -arrayOfDouble1[i4] / arrayOfDouble1[(i3 + 1)];
/* 160 */             for (i7 = i3 + 1; i7 < this.m; i7++) {
/* 161 */               arrayOfDouble[i7][i4] += d2 * arrayOfDouble2[i7];
/*     */             }
/*     */           }
/*     */         }
/* 165 */         if (k != 0)
/*     */         {
/* 170 */           for (int i4 = i3 + 1; i4 < this.n; i4++) {
/* 171 */             this.V[i4][i3] = arrayOfDouble1[i4];
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 179 */     int i3 = Math.min(this.n, this.m + 1);
/* 180 */     if (i1 < this.n) {
/* 181 */       this.s[i1] = arrayOfDouble[i1][i1];
/*     */     }
/* 183 */     if (this.m < i3) {
/* 184 */       this.s[(i3 - 1)] = 0.0D;
/*     */     }
/* 186 */     if (i2 + 1 < i3) {
/* 187 */       arrayOfDouble1[i2] = arrayOfDouble[i2][(i3 - 1)];
/*     */     }
/* 189 */     arrayOfDouble1[(i3 - 1)] = 0.0D;
/*     */     int i8;
/* 193 */     if (j != 0) {
/* 194 */       for (int i4 = i1; i4 < i; i4++) {
/* 195 */         for (int i6 = 0; i6 < this.m; i6++) {
/* 196 */           this.U[i6][i4] = 0.0D;
/*     */         }
/* 198 */         this.U[i4][i4] = 1.0D;
/*     */       }
/* 200 */       for (int i4 = i1 - 1; i4 >= 0; i4--) {
/* 201 */         if (this.s[i4] != 0.0D) {
/* 202 */           for (int i6 = i4 + 1; i6 < i; i6++) {
/* 203 */             double d3 = 0.0D;
/* 204 */             for (i8 = i4; i8 < this.m; i8++) {
/* 205 */               d3 += this.U[i8][i4] * this.U[i8][i6];
/*     */             }
/* 207 */             d3 = -d3 / this.U[i4][i4];
/* 208 */             for (i8 = i4; i8 < this.m; i8++) {
/* 209 */               this.U[i8][i6] += d3 * this.U[i8][i4];
/*     */             }
/*     */           }
/* 212 */           for (int i6 = i4; i6 < this.m; i6++) {
/* 213 */             this.U[i6][i4] = (-this.U[i6][i4]);
/*     */           }
/* 215 */           this.U[i4][i4] = (1.0D + this.U[i4][i4]);
/* 216 */           for (int i6 = 0; i6 < i4 - 1; i6++)
/* 217 */             this.U[i6][i4] = 0.0D;
/*     */         }
/*     */         else {
/* 220 */           for (int i6 = 0; i6 < this.m; i6++) {
/* 221 */             this.U[i6][i4] = 0.0D;
/*     */           }
/* 223 */           this.U[i4][i4] = 1.0D;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 230 */     if (k != 0) {
/* 231 */       for (int i4 = this.n - 1; i4 >= 0; i4--) {
/* 232 */         if (((i4 < i2 ? 1 : 0) & (arrayOfDouble1[i4] != 0.0D ? 1 : 0)) != 0) {
/* 233 */           for (int i6 = i4 + 1; i6 < i; i6++) {
/* 234 */             double d3 = 0.0D;
/* 235 */             for (i8 = i4 + 1; i8 < this.n; i8++) {
/* 236 */               d3 += this.V[i8][i4] * this.V[i8][i6];
/*     */             }
/* 238 */             d3 = -d3 / this.V[(i4 + 1)][i4];
/* 239 */             for (i8 = i4 + 1; i8 < this.n; i8++) {
/* 240 */               this.V[i8][i6] += d3 * this.V[i8][i4];
/*     */             }
/*     */           }
/*     */         }
/* 244 */         for (int i6 = 0; i6 < this.n; i6++) {
/* 245 */           this.V[i6][i4] = 0.0D;
/*     */         }
/* 247 */         this.V[i4][i4] = 1.0D;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 253 */     int i4 = i3 - 1;
/* 254 */     int i6 = 0;
/* 255 */     double d3 = Math.pow(2.0D, -52.0D);
/* 256 */     double d4 = Math.pow(2.0D, -966.0D);
/* 257 */     while (i3 > 0)
/*     */     {
                int tt = 0;
/* 272 */       for (int i9 = i3 - 2; (i9 >= -1) && 
/* 273 */         (i9 != -1); i9--)
/*     */       {tt = i9;
/* 276 */         if (Math.abs(arrayOfDouble1[i9]) <= d4 + d3 * (Math.abs(this.s[i9]) + Math.abs(this.s[(i9 + 1)])))
/*     */         {
/* 278 */           arrayOfDouble1[i9] = 0.0D;
/* 279 */           break;
/*     */         }
/*     */       }
                
/*     */       int i10;
/* 282 */       if (tt == i3 - 2) {
/* 283 */         i10 = 4;
/*     */       }
/*     */       else {
                    int tt11 = 0;
/* 286 */         for (int i11 = i3 - 1; (i11 >= tt) && 
/* 287 */           (i11 != tt); i11--)
/*     */         {
                    tt11 = i11;
/* 290 */           double d7 = (i11 != i3 ? Math.abs(arrayOfDouble1[i11]) : 0.0D) + (i11 != tt + 1 ? Math.abs(arrayOfDouble1[(i11 - 1)]) : 0.0D);
/*     */ 
/* 292 */           if (Math.abs(this.s[i11]) <= d4 + d3 * d7) {
/* 293 */             this.s[i11] = 0.0D;
/* 294 */             break;
/*     */           }
/*     */         }
/* 297 */         if (tt11 == tt) {
/* 298 */           i10 = 3;
/* 299 */         } else if (tt11 == i3 - 1) {
/* 300 */           i10 = 1;
/*     */         } else {
/* 302 */           i10 = 2;
/* 303 */           tt = tt11;
/*     */         }
/*     */       }
/* 306 */       tt++;
                int i9 = tt;
/*     */       double d5;
/*     */       int i13;
/*     */       double d9;
/*     */       double d11;
/*     */       double d13;
/*     */       int i15;
/* 310 */       switch (i10)
/*     */       {
/*     */       case 1:
/* 315 */         d5 = arrayOfDouble1[(i3 - 2)];
/* 316 */         arrayOfDouble1[(i3 - 2)] = 0.0D;
/* 317 */         for (i13 = i3 - 2; i13 >= tt; i13--) {
/* 318 */           d9 = Maths.hypot(this.s[i13], d5);
/* 319 */           d11 = this.s[i13] / d9;
/* 320 */           d13 = d5 / d9;
/* 321 */           this.s[i13] = d9;
/* 322 */           if (i13 != tt) {
/* 323 */             d5 = -d13 * arrayOfDouble1[(i13 - 1)];
/* 324 */             arrayOfDouble1[(i13 - 1)] = (d11 * arrayOfDouble1[(i13 - 1)]);
/*     */           }
/* 326 */           if (k != 0) {
/* 327 */             for (i15 = 0; i15 < this.n; i15++) {
/* 328 */               d9 = d11 * this.V[i15][i13] + d13 * this.V[i15][(i3 - 1)];
/* 329 */               this.V[i15][(i3 - 1)] = (-d13 * this.V[i15][i13] + d11 * this.V[i15][(i3 - 1)]);
/* 330 */               this.V[i15][i13] = d9;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 335 */         break;
/*     */       case 2:
/* 340 */         d5 = arrayOfDouble1[(tt - 1)];
/* 341 */         arrayOfDouble1[(i9 - 1)] = 0.0D;
/* 342 */         for (i13 = i9; i13 < i3; i13++) {
/* 343 */           d9 = Maths.hypot(this.s[i13], d5);
/* 344 */           d11 = this.s[i13] / d9;
/* 345 */           d13 = d5 / d9;
/* 346 */           this.s[i13] = d9;
/* 347 */           d5 = -d13 * arrayOfDouble1[i13];
/* 348 */           arrayOfDouble1[i13] = (d11 * arrayOfDouble1[i13]);
/* 349 */           if (j != 0) {
/* 350 */             for (i15 = 0; i15 < this.m; i15++) {
/* 351 */               d9 = d11 * this.U[i15][i13] + d13 * this.U[i15][(i9 - 1)];
/* 352 */               this.U[i15][(i9 - 1)] = (-d13 * this.U[i15][i13] + d11 * this.U[i15][(i9 - 1)]);
/* 353 */               this.U[i15][i13] = d9;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 358 */         break;
/*     */       case 3:
/* 366 */         d5 = Math.max(Math.max(Math.max(Math.max(Math.abs(this.s[(i3 - 1)]), Math.abs(this.s[(i3 - 2)])), Math.abs(arrayOfDouble1[(i3 - 2)])), Math.abs(this.s[i9])), Math.abs(arrayOfDouble1[i9]));
/*     */ 
/* 369 */         double d8 = this.s[(i3 - 1)] / d5;
/* 370 */         double d10 = this.s[(i3 - 2)] / d5;
/* 371 */         double d12 = arrayOfDouble1[(i3 - 2)] / d5;
/* 372 */         double d14 = this.s[i9] / d5;
/* 373 */         double d15 = arrayOfDouble1[i9] / d5;
/* 374 */         double d16 = ((d10 + d8) * (d10 - d8) + d12 * d12) / 2.0D;
/* 375 */         double d17 = d8 * d12 * (d8 * d12);
/* 376 */         double d18 = 0.0D;
/* 377 */         if (((d16 != 0.0D ? 1 : 0) | (d17 != 0.0D ? 1 : 0)) != 0) {
/* 378 */           d18 = Math.sqrt(d16 * d16 + d17);
/* 379 */           if (d16 < 0.0D) {
/* 380 */             d18 = -d18;
/*     */           }
/* 382 */           d18 = d17 / (d16 + d18);
/*     */         }
/* 384 */         double d19 = (d14 + d8) * (d14 - d8) + d18;
/* 385 */         double d20 = d14 * d15;
/*     */ 
/* 389 */         for (int i16 = i9; i16 < i3 - 1; i16++) {
/* 390 */           double d21 = Maths.hypot(d19, d20);
/* 391 */           double d22 = d19 / d21;
/* 392 */           double d23 = d20 / d21;
/* 393 */           if (i16 != i9) {
/* 394 */             arrayOfDouble1[(i16 - 1)] = d21;
/*     */           }
/* 396 */           d19 = d22 * this.s[i16] + d23 * arrayOfDouble1[i16];
/* 397 */           arrayOfDouble1[i16] = (d22 * arrayOfDouble1[i16] - d23 * this.s[i16]);
/* 398 */           d20 = d23 * this.s[(i16 + 1)];
/* 399 */           this.s[(i16 + 1)] = (d22 * this.s[(i16 + 1)]);
/*     */           int i17;
/* 400 */           if (k != 0) {
/* 401 */             for (i17 = 0; i17 < this.n; i17++) {
/* 402 */               d21 = d22 * this.V[i17][i16] + d23 * this.V[i17][(i16 + 1)];
/* 403 */               this.V[i17][(i16 + 1)] = (-d23 * this.V[i17][i16] + d22 * this.V[i17][(i16 + 1)]);
/* 404 */               this.V[i17][i16] = d21;
/*     */             }
/*     */           }
/* 407 */           d21 = Maths.hypot(d19, d20);
/* 408 */           d22 = d19 / d21;
/* 409 */           d23 = d20 / d21;
/* 410 */           this.s[i16] = d21;
/* 411 */           d19 = d22 * arrayOfDouble1[i16] + d23 * this.s[(i16 + 1)];
/* 412 */           this.s[(i16 + 1)] = (-d23 * arrayOfDouble1[i16] + d22 * this.s[(i16 + 1)]);
/* 413 */           d20 = d23 * arrayOfDouble1[(i16 + 1)];
/* 414 */           arrayOfDouble1[(i16 + 1)] = (d22 * arrayOfDouble1[(i16 + 1)]);
/* 415 */           if ((j != 0) && (i16 < this.m - 1)) {
/* 416 */             for (i17 = 0; i17 < this.m; i17++) {
/* 417 */               d21 = d22 * this.U[i17][i16] + d23 * this.U[i17][(i16 + 1)];
/* 418 */               this.U[i17][(i16 + 1)] = (-d23 * this.U[i17][i16] + d22 * this.U[i17][(i16 + 1)]);
/* 419 */               this.U[i17][i16] = d21;
/*     */             }
/*     */           }
/*     */         }
/* 423 */         arrayOfDouble1[(i3 - 2)] = d19;
/* 424 */         i6 += 1;
/*     */ 
/* 426 */         break;
/*     */       case 4:
/* 434 */         if (this.s[i9] <= 0.0D) {
/* 435 */           this.s[i9] = (this.s[i9] < 0.0D ? -this.s[i9] : 0.0D);
/* 436 */           if (k != 0) {
/* 437 */             for (int i12 = 0; i12 <= i4; i12++) {
/* 438 */               this.V[i12][i9] = (-this.V[i12][i9]);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 445 */         while ((i9 < i4) && 
/* 446 */           (this.s[i9] < this.s[(i9 + 1)]))
/*     */         {
/* 449 */           double d6 = this.s[i9];
/* 450 */           this.s[i9] = this.s[(i9 + 1)];
/* 451 */           this.s[(i9 + 1)] = d6;
/*     */           int i14;
/* 452 */           if ((k != 0) && (i9 < this.n - 1)) {
/* 453 */             for (i14 = 0; i14 < this.n; i14++) {
/* 454 */               d6 = this.V[i14][(i9 + 1)]; this.V[i14][(i9 + 1)] = this.V[i14][i9]; this.V[i14][i9] = d6;
/*     */             }
/*     */           }
/* 457 */           if ((j != 0) && (i9 < this.m - 1)) {
/* 458 */             for (i14 = 0; i14 < this.m; i14++) {
/* 459 */               d6 = this.U[i14][(i9 + 1)]; this.U[i14][(i9 + 1)] = this.U[i14][i9]; this.U[i14][i9] = d6;
/*     */             }
/*     */           }
/* 462 */           i9++;
/*     */         }
/* 464 */         i6 = 0;
/* 465 */         i3--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Matrix getU()
/*     */   {
/* 481 */     return new Matrix(this.U, this.m, Math.min(this.m + 1, this.n));
/*     */   }
/*     */ 
/*     */   public Matrix getV()
/*     */   {
/* 489 */     return new Matrix(this.V, this.n, this.n);
/*     */   }
/*     */ 
/*     */   public double[] getSingularValues()
/*     */   {
/* 497 */     return this.s;
/*     */   }
/*     */ 
/*     */   public Matrix getS()
/*     */   {
/* 505 */     Matrix localMatrix = new Matrix(this.n, this.n);
/* 506 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 507 */     for (int i = 0; i < this.n; i++) {
/* 508 */       for (int j = 0; j < this.n; j++) {
/* 509 */         arrayOfDouble[i][j] = 0.0D;
/*     */       }
/* 511 */       arrayOfDouble[i][i] = this.s[i];
/*     */     }
/* 513 */     return localMatrix;
/*     */   }
/*     */ 
/*     */   public double norm2()
/*     */   {
/* 521 */     return this.s[0];
/*     */   }
/*     */ 
/*     */   public double cond()
/*     */   {
/* 529 */     return this.s[0] / this.s[(Math.min(this.m, this.n) - 1)];
/*     */   }
/*     */ 
/*     */   public int rank()
/*     */   {
/* 537 */     double d1 = Math.pow(2.0D, -52.0D);
/* 538 */     double d2 = Math.max(this.m, this.n) * this.s[0] * d1;
/* 539 */     int i = 0;
/* 540 */     for (int j = 0; j < this.s.length; j++) {
/* 541 */       if (this.s[j] > d2) {
/* 542 */         i++;
/*     */       }
/*     */     }
/* 545 */     return i;
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.SingularValueDecomposition
 * JD-Core Version:    0.6.2
 */