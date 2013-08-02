/*     */ package Jama;
/*     */ 
/*     */ import Jama.util.Maths;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class EigenvalueDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private int n;
/*     */   private boolean issymmetric;
/*     */   private double[] d;
/*     */   private double[] e;
/*     */   private double[][] V;
/*     */   private double[][] H;
/*     */   private double[] ort;
/*     */   private transient double cdivr;
/*     */   private transient double cdivi;
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   private void tred2()
/*     */   {
/*  69 */     for (int i = 0; i < this.n; i++)
/*  70 */       this.d[i] = this.V[(this.n - 1)][i];
/*     */     double d1;
/*  75 */     for (int i = this.n - 1; i > 0; i--)
/*     */     {
/*  79 */       d1 = 0.0D;
/*  80 */       double d2 = 0.0D;
/*  81 */       for (int k = 0; k < i; k++) {
/*  82 */         d1 += Math.abs(this.d[k]);
/*     */       }
/*  84 */       if (d1 == 0.0D) {
/*  85 */         this.e[i] = this.d[(i - 1)];
/*  86 */         for (int k = 0; k < i; k++) {
/*  87 */           this.d[k] = this.V[(i - 1)][k];
/*  88 */           this.V[i][k] = 0.0D;
/*  89 */           this.V[k][i] = 0.0D;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  95 */         for (int k = 0; k < i; k++) {
/*  96 */           this.d[k] /= d1;
/*  97 */           d2 += this.d[k] * this.d[k];
/*     */         }
/*  99 */         double d4 = this.d[(i - 1)];
/* 100 */         double d5 = Math.sqrt(d2);
/* 101 */         if (d4 > 0.0D) {
/* 102 */           d5 = -d5;
/*     */         }
/* 104 */         this.e[i] = (d1 * d5);
/* 105 */         d2 -= d4 * d5;
/* 106 */         this.d[(i - 1)] = (d4 - d5);
/* 107 */         for (int i1 = 0; i1 < i; i1++) {
/* 108 */           this.e[i1] = 0.0D;
/*     */         }
/*     */ 
/* 113 */         for (int i1 = 0; i1 < i; i1++) {
/* 114 */           d4 = this.d[i1];
/* 115 */           this.V[i1][i] = d4;
/* 116 */           d5 = this.e[i1] + this.V[i1][i1] * d4;
/* 117 */           for (int i2 = i1 + 1; i2 <= i - 1; i2++) {
/* 118 */             d5 += this.V[i2][i1] * this.d[i2];
/* 119 */             this.e[i2] += this.V[i2][i1] * d4;
/*     */           }
/* 121 */           this.e[i1] = d5;
/*     */         }
/* 123 */         d4 = 0.0D;
/* 124 */         for (int i1 = 0; i1 < i; i1++) {
/* 125 */           this.e[i1] /= d2;
/* 126 */           d4 += this.e[i1] * this.d[i1];
/*     */         }
/* 128 */         double d6 = d4 / (d2 + d2);
/* 129 */         for (int i3 = 0; i3 < i; i3++) {
/* 130 */           this.e[i3] -= d6 * this.d[i3];
/*     */         }
/* 132 */         for (int i3 = 0; i3 < i; i3++) {
/* 133 */           d4 = this.d[i3];
/* 134 */           d5 = this.e[i3];
/* 135 */           for (int i4 = i3; i4 <= i - 1; i4++) {
/* 136 */             this.V[i4][i3] -= d4 * this.e[i4] + d5 * this.d[i4];
/*     */           }
/* 138 */           this.d[i3] = this.V[(i - 1)][i3];
/* 139 */           this.V[i][i3] = 0.0D;
/*     */         }
/*     */       }
/* 142 */       this.d[i] = d2;
/*     */     }
/*     */ 
/* 147 */     for (int i = 0; i < this.n - 1; i++) {
/* 148 */       this.V[(this.n - 1)][i] = this.V[i][i];
/* 149 */       this.V[i][i] = 1.0D;
/* 150 */       d1 = this.d[(i + 1)];
/* 151 */       if (d1 != 0.0D) {
/* 152 */         for (int j = 0; j <= i; j++) {
/* 153 */           this.d[j] = (this.V[j][(i + 1)] / d1);
/*     */         }
/* 155 */         for (int j = 0; j <= i; j++) {
/* 156 */           double d3 = 0.0D;
/* 157 */           for (int m = 0; m <= i; m++) {
/* 158 */             d3 += this.V[m][(i + 1)] * this.V[m][j];
/*     */           }
/* 160 */           for (int m = 0; m <= i; m++) {
/* 161 */             this.V[m][j] -= d3 * this.d[m];
/*     */           }
/*     */         }
/*     */       }
/* 165 */       for (int j = 0; j <= i; j++) {
/* 166 */         this.V[j][(i + 1)] = 0.0D;
/*     */       }
/*     */     }
/* 169 */     for (int i = 0; i < this.n; i++) {
/* 170 */       this.d[i] = this.V[(this.n - 1)][i];
/* 171 */       this.V[(this.n - 1)][i] = 0.0D;
/*     */     }
/* 173 */     this.V[(this.n - 1)][(this.n - 1)] = 1.0D;
/* 174 */     this.e[0] = 0.0D;
/*     */   }
/*     */ 
/*     */   private void tql2()
/*     */   {
/* 186 */     for (int i = 1; i < this.n; i++) {
/* 187 */       this.e[(i - 1)] = this.e[i];
/*     */     }
/* 189 */     this.e[(this.n - 1)] = 0.0D;
/*     */ 
/* 191 */     double d1 = 0.0D;
/* 192 */     double d2 = 0.0D;
/* 193 */     double d3 = Math.pow(2.0D, -52.0D);
/*     */     int k;
/* 194 */     for (int j = 0; j < this.n; j++)
/*     */     {
/* 198 */       d2 = Math.max(d2, Math.abs(this.d[j]) + Math.abs(this.e[j]));
/* 199 */       k = j;
/* 200 */       while ((k < this.n) && 
/* 201 */         (Math.abs(this.e[k]) > d3 * d2))
/*     */       {
/* 204 */         k++;
/*     */       }
/*     */ 
/* 210 */       if (k > j) {
/* 211 */         int m = 0;
/*     */         do {
/* 213 */           m += 1;
/*     */ 
/* 217 */           double d5 = this.d[j];
/* 218 */           double d6 = (this.d[(j + 1)] - d5) / (2.0D * this.e[j]);
/* 219 */           double d7 = Maths.hypot(d6, 1.0D);
/* 220 */           if (d6 < 0.0D) {
/* 221 */             d7 = -d7;
/*     */           }
/* 223 */           this.d[j] = (this.e[j] / (d6 + d7));
/* 224 */           this.d[(j + 1)] = (this.e[j] * (d6 + d7));
/* 225 */           double d8 = this.d[(j + 1)];
/* 226 */           double d9 = d5 - this.d[j];
/* 227 */           for (int i2 = j + 2; i2 < this.n; i2++) {
/* 228 */             this.d[i2] -= d9;
/*     */           }
/* 230 */           d1 += d9;
/*     */ 
/* 234 */           d6 = this.d[k];
/* 235 */           double d10 = 1.0D;
/* 236 */           double d11 = d10;
/* 237 */           double d12 = d10;
/* 238 */           double d13 = this.e[(j + 1)];
/* 239 */           double d14 = 0.0D;
/* 240 */           double d15 = 0.0D;
/* 241 */           for (int i3 = k - 1; i3 >= j; i3--) {
/* 242 */             d12 = d11;
/* 243 */             d11 = d10;
/* 244 */             d15 = d14;
/* 245 */             d5 = d10 * this.e[i3];
/* 246 */             d9 = d10 * d6;
/* 247 */             d7 = Maths.hypot(d6, this.e[i3]);
/* 248 */             this.e[(i3 + 1)] = (d14 * d7);
/* 249 */             d14 = this.e[i3] / d7;
/* 250 */             d10 = d6 / d7;
/* 251 */             d6 = d10 * this.d[i3] - d14 * d5;
/* 252 */             this.d[(i3 + 1)] = (d9 + d14 * (d10 * d5 + d14 * this.d[i3]));
/*     */ 
/* 256 */             for (int i4 = 0; i4 < this.n; i4++) {
/* 257 */               d9 = this.V[i4][(i3 + 1)];
/* 258 */               this.V[i4][(i3 + 1)] = (d14 * this.V[i4][i3] + d10 * d9);
/* 259 */               this.V[i4][i3] = (d10 * this.V[i4][i3] - d14 * d9);
/*     */             }
/*     */           }
/* 262 */           d6 = -d14 * d15 * d12 * d13 * this.e[j] / d8;
/* 263 */           this.e[j] = (d14 * d6);
/* 264 */           this.d[j] = (d10 * d6);
/*     */         }
/*     */ 
/* 268 */         while (Math.abs(this.e[j]) > d3 * d2);
/*     */       }
/* 270 */       this.d[j] += d1;
/* 271 */       this.e[j] = 0.0D;
/*     */     }
/*     */ 
/* 276 */     for (int j = 0; j < this.n - 1; j++) {
/* 277 */       k = j;
/* 278 */       double d4 = this.d[j];
/* 279 */       for (int i1 = j + 1; i1 < this.n; i1++) {
/* 280 */         if (this.d[i1] < d4) {
/* 281 */           k = i1;
/* 282 */           d4 = this.d[i1];
/*     */         }
/*     */       }
/* 285 */       if (k != j) {
/* 286 */         this.d[k] = this.d[j];
/* 287 */         this.d[j] = d4;
/* 288 */         for (int i1 = 0; i1 < this.n; i1++) {
/* 289 */           d4 = this.V[i1][j];
/* 290 */           this.V[i1][j] = this.V[i1][k];
/* 291 */           this.V[i1][k] = d4;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void orthes()
/*     */   {
/* 306 */     int i = 0;
/* 307 */     int j = this.n - 1;
/*     */ 
/* 309 */     for (int k = i + 1; k <= j - 1; k++)
/*     */     {
/* 313 */       double d1 = 0.0D;
/* 314 */       for (int i1 = k; i1 <= j; i1++) {
/* 315 */         d1 += Math.abs(this.H[i1][(k - 1)]);
/*     */       }
/* 317 */       if (d1 != 0.0D)
/*     */       {
/* 321 */         double d3 = 0.0D;
/* 322 */         for (int i3 = j; i3 >= k; i3--) {
/* 323 */           this.ort[i3] = (this.H[i3][(k - 1)] / d1);
/* 324 */           d3 += this.ort[i3] * this.ort[i3];
/*     */         }
/* 326 */         double d4 = Math.sqrt(d3);
/* 327 */         if (this.ort[k] > 0.0D) {
/* 328 */           d4 = -d4;
/*     */         }
/* 330 */         d3 -= this.ort[k] * d4;
/* 331 */         this.ort[k] -= d4;
/*     */         double d5;
/*     */         int i5;
/* 336 */         for (int i4 = k; i4 < this.n; i4++) {
/* 337 */           d5 = 0.0D;
/* 338 */           for (i5 = j; i5 >= k; i5--) {
/* 339 */             d5 += this.ort[i5] * this.H[i5][i4];
/*     */           }
/* 341 */           d5 /= d3;
/* 342 */           for (i5 = k; i5 <= j; i5++) {
/* 343 */             this.H[i5][i4] -= d5 * this.ort[i5];
/*     */           }
/*     */         }
/*     */ 
/* 347 */         for (int i4 = 0; i4 <= j; i4++) {
/* 348 */           d5 = 0.0D;
/* 349 */           for (i5 = j; i5 >= k; i5--) {
/* 350 */             d5 += this.ort[i5] * this.H[i4][i5];
/*     */           }
/* 352 */           d5 /= d3;
/* 353 */           for (i5 = k; i5 <= j; i5++) {
/* 354 */             this.H[i4][i5] -= d5 * this.ort[i5];
/*     */           }
/*     */         }
/* 357 */         this.ort[k] = (d1 * this.ort[k]);
/* 358 */         this.H[k][(k - 1)] = (d1 * d4);
/*     */       }
/*     */     }
/*     */     int m;
/* 364 */     for (int k = 0; k < this.n; k++) {
/* 365 */       for (m = 0; m < this.n; m++) {
/* 366 */         this.V[k][m] = (k == m ? 1.0D : 0.0D);
/*     */       }
/*     */     }
/*     */ 
/* 370 */     for (int k = j - 1; k >= i + 1; k--)
/* 371 */       if (this.H[k][(k - 1)] != 0.0D) {
/* 372 */         for (m = k + 1; m <= j; m++) {
/* 373 */           this.ort[m] = this.H[m][(k - 1)];
/*     */         }
/* 375 */         for (m = k; m <= j; m++) {
/* 376 */           double d2 = 0.0D;
/* 377 */           for (int i2 = k; i2 <= j; i2++) {
/* 378 */             d2 += this.ort[i2] * this.V[i2][m];
/*     */           }
/*     */ 
/* 381 */           d2 = d2 / this.ort[k] / this.H[k][(k - 1)];
/* 382 */           for (int i2 = k; i2 <= j; i2++)
/* 383 */             this.V[i2][m] += d2 * this.ort[i2];
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private void cdiv(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/* 396 */     if (Math.abs(paramDouble3) > Math.abs(paramDouble4)) {
/* 397 */       d1 = paramDouble4 / paramDouble3;
/* 398 */       d2 = paramDouble3 + d1 * paramDouble4;
/* 399 */       this.cdivr = ((paramDouble1 + d1 * paramDouble2) / d2);
/* 400 */       this.cdivi = ((paramDouble2 - d1 * paramDouble1) / d2);
/*     */     } else {
/* 402 */       d1 = paramDouble3 / paramDouble4;
/* 403 */       d2 = paramDouble4 + d1 * paramDouble3;
/* 404 */       this.cdivr = ((d1 * paramDouble1 + paramDouble2) / d2);
/* 405 */       this.cdivi = ((d1 * paramDouble2 - paramDouble1) / d2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void hqr2()
/*     */   {
/* 421 */     int i = this.n;
/* 422 */     int j = i - 1;
/* 423 */     double d1 = 0;
/* 424 */     int k = i - 1;
/* 425 */     double d2 = Math.pow(2.0D, -52.0D);
/* 426 */     double d3 = 0.0D;
/* 427 */     double d4 = 0.0D; double d5 = 0.0D; double d6 = 0.0D; double d7 = 0.0D; double d8 = 0.0D;
/*     */ 
/* 431 */     double d13 = 0.0D;
/* 432 */     for (int m = 0; m < i; m++) {
/* 433 */       if (((m < d1 ? 1 : 0) | (m > k ? 1 : 0)) != 0) {
/* 434 */         this.d[m] = this.H[m][m];
/* 435 */         this.e[m] = 0.0D;
/*     */       }
/* 437 */       for (int i1 = Math.max(m - 1, 0); i1 < i; i1++) {
/* 438 */         d13 += Math.abs(this.H[m][i1]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 444 */     int m = 0;
/*     */     double d10;
/*     */     double d11;
/*     */     int i2;
/*     */     double d12;
/*     */     int i3;
/* 445 */     while (j >= d1)
/*     */     {
/* 449 */       int i1 = j;
/* 450 */       while (i1 > d1) {
/* 451 */         d7 = Math.abs(this.H[(i1 - 1)][(i1 - 1)]) + Math.abs(this.H[i1][i1]);
/* 452 */         if (d7 == 0.0D) {
/* 453 */           d7 = d13;
/*     */         }
/* 455 */         if (Math.abs(this.H[i1][(i1 - 1)]) < d2 * d7) {
/*     */           break;
/*     */         }
/* 458 */         i1--;
/*     */       }
/*     */ 
/* 464 */       if (i1 == j) {
/* 465 */         this.H[j][j] += d3;
/* 466 */         this.d[j] = this.H[j][j];
/* 467 */         this.e[j] = 0.0D;
/* 468 */         j--;
/* 469 */         m = 0;
/*     */       }
/* 473 */       else if (i1 == j - 1) {
/* 474 */         d10 = this.H[j][(j - 1)] * this.H[(j - 1)][j];
/* 475 */         d4 = (this.H[(j - 1)][(j - 1)] - this.H[j][j]) / 2.0D;
/* 476 */         d5 = d4 * d4 + d10;
/* 477 */         d8 = Math.sqrt(Math.abs(d5));
/* 478 */         this.H[j][j] += d3;
/* 479 */         this.H[(j - 1)][(j - 1)] += d3;
/* 480 */         d11 = this.H[j][j];
/*     */ 
/* 484 */         if (d5 >= 0.0D) {
/* 485 */           if (d4 >= 0.0D)
/* 486 */             d8 = d4 + d8;
/*     */           else {
/* 488 */             d8 = d4 - d8;
/*     */           }
/* 490 */           this.d[(j - 1)] = (d11 + d8);
/* 491 */           this.d[j] = this.d[(j - 1)];
/* 492 */           if (d8 != 0.0D) {
/* 493 */             this.d[j] = (d11 - d10 / d8);
/*     */           }
/* 495 */           this.e[(j - 1)] = 0.0D;
/* 496 */           this.e[j] = 0.0D;
/* 497 */           d11 = this.H[j][(j - 1)];
/* 498 */           d7 = Math.abs(d11) + Math.abs(d8);
/* 499 */           d4 = d11 / d7;
/* 500 */           d5 = d8 / d7;
/* 501 */           d6 = Math.sqrt(d4 * d4 + d5 * d5);
/* 502 */           d4 /= d6;
/* 503 */           d5 /= d6;
/*     */ 
/* 507 */           for (i2 = j - 1; i2 < i; i2++) {
/* 508 */             d8 = this.H[(j - 1)][i2];
/* 509 */             this.H[(j - 1)][i2] = (d5 * d8 + d4 * this.H[j][i2]);
/* 510 */             this.H[j][i2] = (d5 * this.H[j][i2] - d4 * d8);
/*     */           }
/*     */ 
/* 515 */           for (i2 = 0; i2 <= j; i2++) {
/* 516 */             d8 = this.H[i2][(j - 1)];
/* 517 */             this.H[i2][(j - 1)] = (d5 * d8 + d4 * this.H[i2][j]);
/* 518 */             this.H[i2][j] = (d5 * this.H[i2][j] - d4 * d8);
/*     */           }
/*     */ 
/* 523 */           for (i2 = (int)d1; i2 <= k; i2++) {
/* 524 */             d8 = this.V[i2][(j - 1)];
/* 525 */             this.V[i2][(j - 1)] = (d5 * d8 + d4 * this.V[i2][j]);
/* 526 */             this.V[i2][j] = (d5 * this.V[i2][j] - d4 * d8);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 532 */           this.d[(j - 1)] = (d11 + d4);
/* 533 */           this.d[j] = (d11 + d4);
/* 534 */           this.e[(j - 1)] = d8;
/* 535 */           this.e[j] = (-d8);
/*     */         }
/* 537 */         j -= 2;
/* 538 */         m = 0;
/*     */       }
/*     */       else
/*     */       {
/* 546 */         d11 = this.H[j][j];
/* 547 */         d12 = 0.0D;
/* 548 */         d10 = 0.0D;
/* 549 */         if (i1 < j) {
/* 550 */           d12 = this.H[(j - 1)][(j - 1)];
/* 551 */           d10 = this.H[j][(j - 1)] * this.H[(j - 1)][j];
/*     */         }
/*     */ 
/* 556 */         if (m == 10) {
/* 557 */           d3 += d11;
/* 558 */           for (i2 = (int)d1; i2 <= j; i2++) {
/* 559 */             this.H[i2][i2] -= d11;
/*     */           }
/* 561 */           d7 = Math.abs(this.H[j][(j - 1)]) + Math.abs(this.H[(j - 1)][(j - 2)]);
/* 562 */           d11 = d12 = 0.75D * d7;
/* 563 */           d10 = -0.4375D * d7 * d7;
/*     */         }
/*     */ 
/* 568 */         if (m == 30) {
/* 569 */           d7 = (d12 - d11) / 2.0D;
/* 570 */           d7 = d7 * d7 + d10;
/* 571 */           if (d7 > 0.0D) {
/* 572 */             d7 = Math.sqrt(d7);
/* 573 */             if (d12 < d11) {
/* 574 */               d7 = -d7;
/*     */             }
/* 576 */             d7 = d11 - d10 / ((d12 - d11) / 2.0D + d7);
/* 577 */             for (i2 = (int)d1; i2 <= j; i2++) {
/* 578 */               this.H[i2][i2] -= d7;
/*     */             }
/* 580 */             d3 += d7;
/* 581 */             d11 = d12 = d10 = 0.964D;
/*     */           }
/*     */         }
/*     */ 
/* 585 */         m += 1;
/*     */ 
/* 589 */         i2 = j - 2;
/* 590 */         while (i2 >= i1) {
/* 591 */           d8 = this.H[i2][i2];
/* 592 */           d6 = d11 - d8;
/* 593 */           d7 = d12 - d8;
/* 594 */           d4 = (d6 * d7 - d10) / this.H[(i2 + 1)][i2] + this.H[i2][(i2 + 1)];
/* 595 */           d5 = this.H[(i2 + 1)][(i2 + 1)] - d8 - d6 - d7;
/* 596 */           d6 = this.H[(i2 + 2)][(i2 + 1)];
/* 597 */           d7 = Math.abs(d4) + Math.abs(d5) + Math.abs(d6);
/* 598 */           d4 /= d7;
/* 599 */           d5 /= d7;
/* 600 */           d6 /= d7;
/* 601 */           if (i2 == i1) {
/*     */             break;
/*     */           }
/* 604 */           if (Math.abs(this.H[i2][(i2 - 1)]) * (Math.abs(d5) + Math.abs(d6)) < d2 * (Math.abs(d4) * (Math.abs(this.H[(i2 - 1)][(i2 - 1)]) + Math.abs(d8) + Math.abs(this.H[(i2 + 1)][(i2 + 1)]))))
/*     */           {
/*     */             break;
/*     */           }
/*     */ 
/* 609 */           i2--;
/*     */         }
/*     */ 
/* 612 */         for (i3 = i2 + 2; i3 <= j; i3++) {
/* 613 */           this.H[i3][(i3 - 2)] = 0.0D;
/* 614 */           if (i3 > i2 + 2) {
/* 615 */             this.H[i3][(i3 - 3)] = 0.0D;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 622 */         for (i3 = i2; i3 <= j - 1; i3++) {
/* 623 */           int i4 = i3 != j - 1 ? 1 : 0;
/* 624 */           if (i3 != i2) {
/* 625 */             d4 = this.H[i3][(i3 - 1)];
/* 626 */             d5 = this.H[(i3 + 1)][(i3 - 1)];
/* 627 */             d6 = i4 != 0 ? this.H[(i3 + 2)][(i3 - 1)] : 0.0D;
/* 628 */             d11 = Math.abs(d4) + Math.abs(d5) + Math.abs(d6);
/* 629 */             if (d11 != 0.0D)
/*     */             {
/* 632 */               d4 /= d11;
/* 633 */               d5 /= d11;
/* 634 */               d6 /= d11;
/*     */             }
/*     */           } else {
/* 637 */             d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
/* 638 */             if (d4 < 0.0D) {
/* 639 */               d7 = -d7;
/*     */             }
/* 641 */             if (d7 != 0.0D) {
/* 642 */               if (i3 != i2)
/* 643 */                 this.H[i3][(i3 - 1)] = (-d7 * d11);
/* 644 */               else if (i1 != i2) {
/* 645 */                 this.H[i3][(i3 - 1)] = (-this.H[i3][(i3 - 1)]);
/*     */               }
/* 647 */               d4 += d7;
/* 648 */               d11 = d4 / d7;
/* 649 */               d12 = d5 / d7;
/* 650 */               d8 = d6 / d7;
/* 651 */               d5 /= d4;
/* 652 */               d6 /= d4;
/*     */ 
/* 656 */               for (int i5 = i3; i5 < i; i5++) {
/* 657 */                 d4 = this.H[i3][i5] + d5 * this.H[(i3 + 1)][i5];
/* 658 */                 if (i4 != 0) {
/* 659 */                   d4 += d6 * this.H[(i3 + 2)][i5];
/* 660 */                   this.H[(i3 + 2)][i5] -= d4 * d8;
/*     */                 }
/* 662 */                 this.H[i3][i5] -= d4 * d11;
/* 663 */                 this.H[(i3 + 1)][i5] -= d4 * d12;
/*     */               }
/*     */ 
/* 668 */               for (int i5 = 0; i5 <= Math.min(j, i3 + 3); i5++) {
/* 669 */                 d4 = d11 * this.H[i5][i3] + d12 * this.H[i5][(i3 + 1)];
/* 670 */                 if (i4 != 0) {
/* 671 */                   d4 += d8 * this.H[i5][(i3 + 2)];
/* 672 */                   this.H[i5][(i3 + 2)] -= d4 * d6;
/*     */                 }
/* 674 */                 this.H[i5][i3] -= d4;
/* 675 */                 this.H[i5][(i3 + 1)] -= d4 * d5;
/*     */               }
/*     */ 
/* 680 */               for (int i5 = (int)d1; i5 <= k; i5++) {
/* 681 */                 d4 = d11 * this.V[i5][i3] + d12 * this.V[i5][(i3 + 1)];
/* 682 */                 if (i4 != 0) {
/* 683 */                   d4 += d8 * this.V[i5][(i3 + 2)];
/* 684 */                   this.V[i5][(i3 + 2)] -= d4 * d6;
/*     */                 }
/* 686 */                 this.V[i5][i3] -= d4;
/* 687 */                 this.V[i5][(i3 + 1)] -= d4 * d5;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 696 */     if (d13 == 0.0D)
/*     */       return;
/*     */     double d14;
/* 700 */     for (j = i - 1; j >= 0; j--) {
/* 701 */       d4 = this.d[j];
/* 702 */       d5 = this.e[j];
/*     */       double d9;
/* 706 */       if (d5 == 0.0D) {
/* 707 */         int i1 = j;
/* 708 */         this.H[j][j] = 1.0D;
/* 709 */         for (i2 = j - 1; i2 >= 0; i2--) {
/* 710 */           d10 = this.H[i2][i2] - d4;
/* 711 */           d6 = 0.0D;
/* 712 */           for (i3 = i1; i3 <= j; i3++) {
/* 713 */             d6 += this.H[i2][i3] * this.H[i3][j];
/*     */           }
/* 715 */           if (this.e[i2] < 0.0D) {
/* 716 */             d8 = d10;
/* 717 */             d7 = d6;
/*     */           } else {
/* 719 */             i1 = i2;
/* 720 */             if (this.e[i2] == 0.0D) {
/* 721 */               if (d10 != 0.0D)
/* 722 */                 this.H[i2][j] = (-d6 / d10);
/*     */               else {
/* 724 */                 this.H[i2][j] = (-d6 / (d2 * d13));
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 730 */               d11 = this.H[i2][(i2 + 1)];
/* 731 */               d12 = this.H[(i2 + 1)][i2];
/* 732 */               d5 = (this.d[i2] - d4) * (this.d[i2] - d4) + this.e[i2] * this.e[i2];
/* 733 */               d9 = (d11 * d7 - d8 * d6) / d5;
/* 734 */               this.H[i2][j] = d9;
/* 735 */               if (Math.abs(d11) > Math.abs(d8))
/* 736 */                 this.H[(i2 + 1)][j] = ((-d6 - d10 * d9) / d11);
/*     */               else {
/* 738 */                 this.H[(i2 + 1)][j] = ((-d7 - d12 * d9) / d8);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 744 */             d9 = Math.abs(this.H[i2][j]);
/* 745 */             if (d2 * d9 * d9 > 1.0D) {
/* 746 */               for (i3 = i2; i3 <= j; i3++) {
/* 747 */                 this.H[i3][j] /= d9;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/* 755 */       else if (d5 < 0.0D) {
/* 756 */         int i1 = j - 1;
/*     */ 
/* 760 */         if (Math.abs(this.H[j][(j - 1)]) > Math.abs(this.H[(j - 1)][j])) {
/* 761 */           this.H[(j - 1)][(j - 1)] = (d5 / this.H[j][(j - 1)]);
/* 762 */           this.H[(j - 1)][j] = (-(this.H[j][j] - d4) / this.H[j][(j - 1)]);
/*     */         } else {
/* 764 */           cdiv(0.0D, -this.H[(j - 1)][j], this.H[(j - 1)][(j - 1)] - d4, d5);
/* 765 */           this.H[(j - 1)][(j - 1)] = this.cdivr;
/* 766 */           this.H[(j - 1)][j] = this.cdivi;
/*     */         }
/* 768 */         this.H[j][(j - 1)] = 0.0D;
/* 769 */         this.H[j][j] = 1.0D;
/* 770 */         for (i2 = j - 2; i2 >= 0; i2--)
/*     */         {
/* 772 */           d14 = 0.0D;
/* 773 */           double d15 = 0.0D;
/* 774 */           for (int i6 = i1; i6 <= j; i6++) {
/* 775 */             d14 += this.H[i2][i6] * this.H[i6][(j - 1)];
/* 776 */             d15 += this.H[i2][i6] * this.H[i6][j];
/*     */           }
/* 778 */           d10 = this.H[i2][i2] - d4;
/*     */ 
/* 780 */           if (this.e[i2] < 0.0D) {
/* 781 */             d8 = d10;
/* 782 */             d6 = d14;
/* 783 */             d7 = d15;
/*     */           } else {
/* 785 */             i1 = i2;
/* 786 */             if (this.e[i2] == 0.0D) {
/* 787 */               cdiv(-d14, -d15, d10, d5);
/* 788 */               this.H[i2][(j - 1)] = this.cdivr;
/* 789 */               this.H[i2][j] = this.cdivi;
/*     */             }
/*     */             else
/*     */             {
/* 794 */               d11 = this.H[i2][(i2 + 1)];
/* 795 */               d12 = this.H[(i2 + 1)][i2];
/* 796 */               double d16 = (this.d[i2] - d4) * (this.d[i2] - d4) + this.e[i2] * this.e[i2] - d5 * d5;
/* 797 */               double d17 = (this.d[i2] - d4) * 2.0D * d5;
/* 798 */               if (((d16 == 0.0D ? 1 : 0) & (d17 == 0.0D ? 1 : 0)) != 0) {
/* 799 */                 d16 = d2 * d13 * (Math.abs(d10) + Math.abs(d5) + Math.abs(d11) + Math.abs(d12) + Math.abs(d8));
/*     */               }
/*     */ 
/* 802 */               cdiv(d11 * d6 - d8 * d14 + d5 * d15, d11 * d7 - d8 * d15 - d5 * d14, d16, d17);
/* 803 */               this.H[i2][(j - 1)] = this.cdivr;
/* 804 */               this.H[i2][j] = this.cdivi;
/* 805 */               if (Math.abs(d11) > Math.abs(d8) + Math.abs(d5)) {
/* 806 */                 this.H[(i2 + 1)][(j - 1)] = ((-d14 - d10 * this.H[i2][(j - 1)] + d5 * this.H[i2][j]) / d11);
/* 807 */                 this.H[(i2 + 1)][j] = ((-d15 - d10 * this.H[i2][j] - d5 * this.H[i2][(j - 1)]) / d11);
/*     */               } else {
/* 809 */                 cdiv(-d6 - d12 * this.H[i2][(j - 1)], -d7 - d12 * this.H[i2][j], d8, d5);
/* 810 */                 this.H[(i2 + 1)][(j - 1)] = this.cdivr;
/* 811 */                 this.H[(i2 + 1)][j] = this.cdivi;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 817 */             d9 = Math.max(Math.abs(this.H[i2][(j - 1)]), Math.abs(this.H[i2][j]));
/* 818 */             if (d2 * d9 * d9 > 1.0D) {
/* 819 */               for (int i6 = i2; i6 <= j; i6++) {
/* 820 */                 this.H[i6][(j - 1)] /= d9;
/* 821 */                 this.H[i6][j] /= d9;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 831 */     for (int i1 = 0; i1 < i; i1++) {
/* 832 */       if (((i1 < d1 ? 1 : 0) | (i1 > k ? 1 : 0)) != 0) {
/* 833 */         for (i2 = i1; i2 < i; i2++) {
/* 834 */           this.V[i1][i2] = this.H[i1][i2];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 841 */     for (int i1 = i - 1; i1 >= d1; i1--)
/* 842 */       for (i2 = (int)d1; i2 <= k; i2++) {
/* 843 */         d8 = 0.0D;
/* 844 */         for (d14 = d1; d14 <= Math.min(i1, k); d14++) {
/* 845 */           d8 += this.V[i2][(int)d14] * this.H[(int)d14][i1];
/*     */         }
/* 847 */         this.V[i2][i1] = d8;
/*     */       }
/*     */   }
/*     */ 
/*     */   public EigenvalueDecomposition(Matrix paramMatrix)
/*     */   {
/* 863 */     double[][] arrayOfDouble = paramMatrix.getArray();
/* 864 */     this.n = paramMatrix.getColumnDimension();
/* 865 */     this.V = new double[this.n][this.n];
/* 866 */     this.d = new double[this.n];
/* 867 */     this.e = new double[this.n];
/*     */ 
/* 869 */     this.issymmetric = true;
/*     */     int j;
/* 870 */     for (int i = 0; (i < this.n & this.issymmetric); i++) {
/* 871 */       for (j = 0; (j < this.n & this.issymmetric); j++) {
/* 872 */         this.issymmetric = (arrayOfDouble[j][i] == arrayOfDouble[i][j]);
/*     */       }
/*     */     }
/*     */ 
/* 876 */     if (this.issymmetric) {
/* 877 */       for (int i = 0; i < this.n; i++) {
/* 878 */         for (j = 0; j < this.n; j++) {
/* 879 */           this.V[i][j] = arrayOfDouble[i][j];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 884 */       tred2();
/*     */ 
/* 887 */       tql2();
/*     */     }
/*     */     else {
/* 890 */       this.H = new double[this.n][this.n];
/* 891 */       this.ort = new double[this.n];
/*     */ 
/* 893 */       for (int i = 0; i < this.n; i++) {
/* 894 */         for (j = 0; j < this.n; j++) {
/* 895 */           this.H[j][i] = arrayOfDouble[j][i];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 900 */       orthes();
/*     */ 
/* 903 */       hqr2();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Matrix getV()
/*     */   {
/* 916 */     return new Matrix(this.V, this.n, this.n);
/*     */   }
/*     */ 
/*     */   public double[] getRealEigenvalues()
/*     */   {
/* 924 */     return this.d;
/*     */   }
/*     */ 
/*     */   public double[] getImagEigenvalues()
/*     */   {
/* 932 */     return this.e;
/*     */   }
/*     */ 
/*     */   public Matrix getD()
/*     */   {
/* 940 */     Matrix localMatrix = new Matrix(this.n, this.n);
/* 941 */     double[][] arrayOfDouble = localMatrix.getArray();
/* 942 */     for (int i = 0; i < this.n; i++) {
/* 943 */       for (int j = 0; j < this.n; j++) {
/* 944 */         arrayOfDouble[i][j] = 0.0D;
/*     */       }
/* 946 */       arrayOfDouble[i][i] = this.d[i];
/* 947 */       if (this.e[i] > 0.0D)
/* 948 */         arrayOfDouble[i][(i + 1)] = this.e[i];
/* 949 */       else if (this.e[i] < 0.0D) {
/* 950 */         arrayOfDouble[i][(i - 1)] = this.e[i];
/*     */       }
/*     */     }
/* 953 */     return localMatrix;
/*     */   }
/*     */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.EigenvalueDecomposition
 * JD-Core Version:    0.6.2
 */