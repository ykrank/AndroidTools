package com.github.ykrank.test;

public class CouldMusicDecrypt {
    public static final int CRLF = 4;
    private static int[] DECODE = null;
    private static int[] DECODE_WEBSAFE = null;
    public static final int DEFAULT = 0;
    private static final int EQUALS = -2;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    private static final int SKIP = -1;
    public static final int URL_SAFE = 8;
    static final String key = "Encrypt";
    private int[] m_alphabet;
    public int m_op;
    public byte[] m_output;
    private int m_state;
    private int m_value;

    static {
        int[] v0 = new int[256];
        v0[0] = -1;
        v0[1] = -1;
        v0[2] = -1;
        v0[3] = -1;
        v0[4] = -1;
        v0[5] = -1;
        v0[6] = -1;
        v0[7] = -1;
        v0[8] = -1;
        v0[9] = -1;
        v0[10] = -1;
        v0[11] = -1;
        v0[12] = -1;
        v0[13] = -1;
        v0[14] = -1;
        v0[15] = -1;
        v0[16] = -1;
        v0[17] = -1;
        v0[18] = -1;
        v0[19] = -1;
        v0[20] = -1;
        v0[21] = -1;
        v0[22] = -1;
        v0[23] = -1;
        v0[24] = -1;
        v0[25] = -1;
        v0[26] = -1;
        v0[27] = -1;
        v0[28] = -1;
        v0[29] = -1;
        v0[30] = -1;
        v0[31] = -1;
        v0[32] = -1;
        v0[33] = -1;
        v0[34] = -1;
        v0[35] = -1;
        v0[36] = -1;
        v0[37] = -1;
        v0[38] = -1;
        v0[39] = -1;
        v0[40] = -1;
        v0[41] = -1;
        v0[42] = -1;
        v0[43] = 62;
        v0[44] = -1;
        v0[45] = -1;
        v0[46] = -1;
        v0[47] = 63;
        v0[48] = 52;
        v0[49] = 53;
        v0[50] = 54;
        v0[51] = 55;
        v0[52] = 56;
        v0[53] = 57;
        v0[54] = 58;
        v0[55] = 59;
        v0[56] = 60;
        v0[57] = 61;
        v0[58] = -1;
        v0[59] = -1;
        v0[60] = -1;
        v0[61] = -2;
        v0[62] = -1;
        v0[63] = -1;
        v0[64] = -1;
        v0[66] = 1;
        v0[67] = 2;
        v0[68] = 3;
        v0[69] = 4;
        v0[70] = 5;
        v0[71] = 6;
        v0[72] = 7;
        v0[73] = 8;
        v0[74] = 9;
        v0[75] = 10;
        v0[76] = 11;
        v0[77] = 12;
        v0[78] = 13;
        v0[79] = 14;
        v0[80] = 15;
        v0[81] = 16;
        v0[82] = 17;
        v0[83] = 18;
        v0[84] = 19;
        v0[85] = 20;
        v0[86] = 21;
        v0[87] = 22;
        v0[88] = 23;
        v0[89] = 24;
        v0[90] = 25;
        v0[91] = -1;
        v0[92] = -1;
        v0[93] = -1;
        v0[94] = -1;
        v0[95] = -1;
        v0[96] = -1;
        v0[97] = 26;
        v0[98] = 27;
        v0[99] = 28;
        v0[100] = 29;
        v0[101] = 30;
        v0[102] = 31;
        v0[103] = 32;
        v0[104] = 33;
        v0[105] = 34;
        v0[106] = 35;
        v0[107] = 36;
        v0[108] = 37;
        v0[109] = 38;
        v0[110] = 39;
        v0[111] = 40;
        v0[112] = 41;
        v0[113] = 42;
        v0[114] = 43;
        v0[115] = 44;
        v0[116] = 45;
        v0[117] = 46;
        v0[118] = 47;
        v0[119] = 48;
        v0[120] = 49;
        v0[121] = 50;
        v0[122] = 51;
        v0[123] = -1;
        v0[124] = -1;
        v0[125] = -1;
        v0[126] = -1;
        v0[127] = -1;
        v0[128] = -1;
        v0[129] = -1;
        v0[130] = -1;
        v0[131] = -1;
        v0[132] = -1;
        v0[133] = -1;
        v0[134] = -1;
        v0[135] = -1;
        v0[136] = -1;
        v0[137] = -1;
        v0[138] = -1;
        v0[139] = -1;
        v0[140] = -1;
        v0[141] = -1;
        v0[142] = -1;
        v0[143] = -1;
        v0[144] = -1;
        v0[145] = -1;
        v0[146] = -1;
        v0[147] = -1;
        v0[148] = -1;
        v0[149] = -1;
        v0[150] = -1;
        v0[151] = -1;
        v0[152] = -1;
        v0[153] = -1;
        v0[154] = -1;
        v0[155] = -1;
        v0[156] = -1;
        v0[157] = -1;
        v0[158] = -1;
        v0[159] = -1;
        v0[160] = -1;
        v0[161] = -1;
        v0[162] = -1;
        v0[163] = -1;
        v0[164] = -1;
        v0[165] = -1;
        v0[166] = -1;
        v0[167] = -1;
        v0[168] = -1;
        v0[169] = -1;
        v0[170] = -1;
        v0[171] = -1;
        v0[172] = -1;
        v0[173] = -1;
        v0[174] = -1;
        v0[175] = -1;
        v0[176] = -1;
        v0[177] = -1;
        v0[178] = -1;
        v0[179] = -1;
        v0[180] = -1;
        v0[181] = -1;
        v0[182] = -1;
        v0[183] = -1;
        v0[184] = -1;
        v0[185] = -1;
        v0[186] = -1;
        v0[187] = -1;
        v0[188] = -1;
        v0[189] = -1;
        v0[190] = -1;
        v0[191] = -1;
        v0[192] = -1;
        v0[193] = -1;
        v0[194] = -1;
        v0[195] = -1;
        v0[196] = -1;
        v0[197] = -1;
        v0[198] = -1;
        v0[199] = -1;
        v0[200] = -1;
        v0[201] = -1;
        v0[202] = -1;
        v0[203] = -1;
        v0[204] = -1;
        v0[205] = -1;
        v0[206] = -1;
        v0[207] = -1;
        v0[208] = -1;
        v0[209] = -1;
        v0[210] = -1;
        v0[211] = -1;
        v0[212] = -1;
        v0[213] = -1;
        v0[214] = -1;
        v0[215] = -1;
        v0[216] = -1;
        v0[217] = -1;
        v0[218] = -1;
        v0[219] = -1;
        v0[220] = -1;
        v0[221] = -1;
        v0[222] = -1;
        v0[223] = -1;
        v0[224] = -1;
        v0[225] = -1;
        v0[226] = -1;
        v0[227] = -1;
        v0[228] = -1;
        v0[229] = -1;
        v0[230] = -1;
        v0[231] = -1;
        v0[232] = -1;
        v0[233] = -1;
        v0[234] = -1;
        v0[235] = -1;
        v0[236] = -1;
        v0[237] = -1;
        v0[238] = -1;
        v0[239] = -1;
        v0[240] = -1;
        v0[241] = -1;
        v0[242] = -1;
        v0[243] = -1;
        v0[244] = -1;
        v0[245] = -1;
        v0[246] = -1;
        v0[247] = -1;
        v0[248] = -1;
        v0[249] = -1;
        v0[250] = -1;
        v0[251] = -1;
        v0[252] = -1;
        v0[253] = -1;
        v0[254] = -1;
        v0[255] = -1;
        CouldMusicDecrypt.DECODE = v0;
        v0 = new int[256];
        v0[0] = -1;
        v0[1] = -1;
        v0[2] = -1;
        v0[3] = -1;
        v0[4] = -1;
        v0[5] = -1;
        v0[6] = -1;
        v0[7] = -1;
        v0[8] = -1;
        v0[9] = -1;
        v0[10] = -1;
        v0[11] = -1;
        v0[12] = -1;
        v0[13] = -1;
        v0[14] = -1;
        v0[15] = -1;
        v0[16] = -1;
        v0[17] = -1;
        v0[18] = -1;
        v0[19] = -1;
        v0[20] = -1;
        v0[21] = -1;
        v0[22] = -1;
        v0[23] = -1;
        v0[24] = -1;
        v0[25] = -1;
        v0[26] = -1;
        v0[27] = -1;
        v0[28] = -1;
        v0[29] = -1;
        v0[30] = -1;
        v0[31] = -1;
        v0[32] = -1;
        v0[33] = -1;
        v0[34] = -1;
        v0[35] = -1;
        v0[36] = -1;
        v0[37] = -1;
        v0[38] = -1;
        v0[39] = -1;
        v0[40] = -1;
        v0[41] = -1;
        v0[42] = -1;
        v0[43] = -1;
        v0[44] = -1;
        v0[45] = 62;
        v0[46] = -1;
        v0[47] = -1;
        v0[48] = 52;
        v0[49] = 53;
        v0[50] = 54;
        v0[51] = 55;
        v0[52] = 56;
        v0[53] = 57;
        v0[54] = 58;
        v0[55] = 59;
        v0[56] = 60;
        v0[57] = 61;
        v0[58] = -1;
        v0[59] = -1;
        v0[60] = -1;
        v0[61] = -2;
        v0[62] = -1;
        v0[63] = -1;
        v0[64] = -1;
        v0[66] = 1;
        v0[67] = 2;
        v0[68] = 3;
        v0[69] = 4;
        v0[70] = 5;
        v0[71] = 6;
        v0[72] = 7;
        v0[73] = 8;
        v0[74] = 9;
        v0[75] = 10;
        v0[76] = 11;
        v0[77] = 12;
        v0[78] = 13;
        v0[79] = 14;
        v0[80] = 15;
        v0[81] = 16;
        v0[82] = 17;
        v0[83] = 18;
        v0[84] = 19;
        v0[85] = 20;
        v0[86] = 21;
        v0[87] = 22;
        v0[88] = 23;
        v0[89] = 24;
        v0[90] = 25;
        v0[91] = -1;
        v0[92] = -1;
        v0[93] = -1;
        v0[94] = -1;
        v0[95] = 63;
        v0[96] = -1;
        v0[97] = 26;
        v0[98] = 27;
        v0[99] = 28;
        v0[100] = 29;
        v0[101] = 30;
        v0[102] = 31;
        v0[103] = 32;
        v0[104] = 33;
        v0[105] = 34;
        v0[106] = 35;
        v0[107] = 36;
        v0[108] = 37;
        v0[109] = 38;
        v0[110] = 39;
        v0[111] = 40;
        v0[112] = 41;
        v0[113] = 42;
        v0[114] = 43;
        v0[115] = 44;
        v0[116] = 45;
        v0[117] = 46;
        v0[118] = 47;
        v0[119] = 48;
        v0[120] = 49;
        v0[121] = 50;
        v0[122] = 51;
        v0[123] = -1;
        v0[124] = -1;
        v0[125] = -1;
        v0[126] = -1;
        v0[127] = -1;
        v0[128] = -1;
        v0[129] = -1;
        v0[130] = -1;
        v0[131] = -1;
        v0[132] = -1;
        v0[133] = -1;
        v0[134] = -1;
        v0[135] = -1;
        v0[136] = -1;
        v0[137] = -1;
        v0[138] = -1;
        v0[139] = -1;
        v0[140] = -1;
        v0[141] = -1;
        v0[142] = -1;
        v0[143] = -1;
        v0[144] = -1;
        v0[145] = -1;
        v0[146] = -1;
        v0[147] = -1;
        v0[148] = -1;
        v0[149] = -1;
        v0[150] = -1;
        v0[151] = -1;
        v0[152] = -1;
        v0[153] = -1;
        v0[154] = -1;
        v0[155] = -1;
        v0[156] = -1;
        v0[157] = -1;
        v0[158] = -1;
        v0[159] = -1;
        v0[160] = -1;
        v0[161] = -1;
        v0[162] = -1;
        v0[163] = -1;
        v0[164] = -1;
        v0[165] = -1;
        v0[166] = -1;
        v0[167] = -1;
        v0[168] = -1;
        v0[169] = -1;
        v0[170] = -1;
        v0[171] = -1;
        v0[172] = -1;
        v0[173] = -1;
        v0[174] = -1;
        v0[175] = -1;
        v0[176] = -1;
        v0[177] = -1;
        v0[178] = -1;
        v0[179] = -1;
        v0[180] = -1;
        v0[181] = -1;
        v0[182] = -1;
        v0[183] = -1;
        v0[184] = -1;
        v0[185] = -1;
        v0[186] = -1;
        v0[187] = -1;
        v0[188] = -1;
        v0[189] = -1;
        v0[190] = -1;
        v0[191] = -1;
        v0[192] = -1;
        v0[193] = -1;
        v0[194] = -1;
        v0[195] = -1;
        v0[196] = -1;
        v0[197] = -1;
        v0[198] = -1;
        v0[199] = -1;
        v0[200] = -1;
        v0[201] = -1;
        v0[202] = -1;
        v0[203] = -1;
        v0[204] = -1;
        v0[205] = -1;
        v0[206] = -1;
        v0[207] = -1;
        v0[208] = -1;
        v0[209] = -1;
        v0[210] = -1;
        v0[211] = -1;
        v0[212] = -1;
        v0[213] = -1;
        v0[214] = -1;
        v0[215] = -1;
        v0[216] = -1;
        v0[217] = -1;
        v0[218] = -1;
        v0[219] = -1;
        v0[220] = -1;
        v0[221] = -1;
        v0[222] = -1;
        v0[223] = -1;
        v0[224] = -1;
        v0[225] = -1;
        v0[226] = -1;
        v0[227] = -1;
        v0[228] = -1;
        v0[229] = -1;
        v0[230] = -1;
        v0[231] = -1;
        v0[232] = -1;
        v0[233] = -1;
        v0[234] = -1;
        v0[235] = -1;
        v0[236] = -1;
        v0[237] = -1;
        v0[238] = -1;
        v0[239] = -1;
        v0[240] = -1;
        v0[241] = -1;
        v0[242] = -1;
        v0[243] = -1;
        v0[244] = -1;
        v0[245] = -1;
        v0[246] = -1;
        v0[247] = -1;
        v0[248] = -1;
        v0[249] = -1;
        v0[250] = -1;
        v0[251] = -1;
        v0[252] = -1;
        v0[253] = -1;
        v0[254] = -1;
        v0[255] = -1;
        CouldMusicDecrypt.DECODE_WEBSAFE = v0;
    }

    public CouldMusicDecrypt() {
        super();
    }

    public void Decoder(int arg3, byte[] arg4) {
        this.m_output = arg4;
        int[] v0 = (arg3 & 8) == 0 ? CouldMusicDecrypt.DECODE : CouldMusicDecrypt.DECODE_WEBSAFE;
        this.m_alphabet = v0;
        this.m_state = 0;
        this.m_value = 0;
    }

    public static String c(String arg10) {
        String v6;
        String v7 = null;
        if (arg10 == null) {
            v6 = v7;
            return v6;
        }

        try {
            byte[] v0 = new CouldMusicDecrypt().decode(arg10, 0);
            int v5 = v0.length;
            int v4 = "Encrypt".length();
            int v2 = 0;
            int v3;
            for (v3 = 0; v2 < v5; ++v3) {
                if (v3 >= v4) {
                    v3 = 0;
                }

                v0[v2] = ((byte) (v0[v2] ^ "Encrypt".charAt(v3)));
                ++v2;
            }

            v6 = new String(v0);
        } catch (Exception v1) {
            v6 = v7;
            v1.printStackTrace();
        }

        return v6;
    }

    public byte[] decode(String arg2, int arg3) {
        return this.decode(arg2.getBytes(), arg3);
    }

    public byte[] decode(byte[] arg3, int arg4) {
        return this.decode(arg3, 0, arg3.length, arg4);
    }

    public byte[] decode(byte[] arg5, int arg6, int arg7, int arg8) {
        byte[] v0;
        this.Decoder(arg8, new byte[arg7 * 3 / 4]);
        if (!this.process(arg5, arg6, arg7, true)) {
            throw new IllegalArgumentException("bad base-64");
        }

        if (this.m_op == this.m_output.length) {
            v0 = this.m_output;
        } else {
            v0 = new byte[this.m_op];
            System.arraycopy(this.m_output, 0, v0, 0, this.m_op);
        }

        return v0;
    }

    public int maxOutputSize(int arg2) {
        return arg2 * 3 / 4 + 10;
    }

    public boolean process(byte[] arg12, int arg13, int arg14, boolean arg15) {
        int v3 = 0;
        if (this.m_state == 6) {
            return false;
        } else {
            int v5 = arg13;
            arg14 += arg13;
            int v7 = this.m_state;
            int v8 = this.m_value;
            int v2 = 0;
            byte[] v4 = this.m_output;
            int[] v0 = this.m_alphabet;
//            label_12:
            while (v5 < arg14) {
                boolean label_14 = false;
                if (v7 == 0) {
                    while (v5 + 4 <= arg14) {
                        v8 = v0[arg12[v5] & 255] << 18 | v0[arg12[v5 + 1] & 255] << 12 | v0[arg12[v5 + 2] & 255] << 6 | v0[arg12[v5 + 3] & 255];
                        if (v8 < 0) {
                            break;
                        }

                        v4[v2 + 2] = ((byte) v8);
                        v4[v2 + 1] = ((byte) (v8 >> 8));
                        v4[v2] = ((byte) (v8 >> 16));
                        v2 += 3;
                        v5 += 4;
                    }

                    if (v5 < arg14) {
                        label_14 = false;
                    } else {
                        v3 = v2;
                        label_14 = true;
                    }
                }

                if (!label_14) {
                    int v6 = v5 + 1;
                    int v1 = v0[arg12[v5] & 255];
                    switch (v7) {
                        case 0: {
                            if (v1 >= 0) {
                                v8 = v1;
                                ++v7;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -1) {
                                break;
                            }

                            this.m_state = 6;
                            return false;
                        }
                        case 1: {
                            if (v1 >= 0) {
                                v8 = v8 << 6 | v1;
                                ++v7;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -1) {
                                break;
                            }

                            this.m_state = 6;
                            return false;
                        }
                        case 2: {
                            if (v1 >= 0) {
                                v8 = v8 << 6 | v1;
                                ++v7;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -2) {
                                v4[v2] = ((byte) (v8 >> 4));
                                v7 = 4;
                                ++v2;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -1) {
                                break;
                            }

                            this.m_state = 6;
                            return false;
                        }
                        case 3: {
                            if (v1 >= 0) {
                                v8 = v8 << 6 | v1;
                                v4[v2 + 2] = ((byte) v8);
                                v4[v2 + 1] = ((byte) (v8 >> 8));
                                v4[v2] = ((byte) (v8 >> 16));
                                v2 += 3;
                                v7 = 0;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -2) {
                                v4[v2 + 1] = ((byte) (v8 >> 2));
                                v4[v2] = ((byte) (v8 >> 10));
                                v2 += 2;
                                v7 = 5;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -1) {
                                break;
                            }

                            this.m_state = 6;
                            return false;
                        }
                        case 4: {
                            if (v1 == -2) {
                                ++v7;
                                v5 = v6;
                                continue;
                            }

                            if (v1 == -1) {
                                break;
                            }

                            this.m_state = 6;
                            return false;
                        }
                        case 5: {
                            if (v1 == -1) {
                                break;
                            }

                            this.m_state = 6;
                            return false;
                        }
                    }

                    //label_66:
                    v5 = v6;
                }
            }

            v3 = v2;
//            label_14:
            if (!arg15) {
                this.m_state = v7;
                this.m_value = v8;
                this.m_op = v3;
                return true;
            }

            switch (v7) {
                case 0: {
                    v2 = v3;

                    this.m_state = v7;
                    this.m_op = v2;
                    return true;
                }
                case 1: {
                    this.m_state = 6;
                    return false;
                }
                case 2: {
                    v2 = v3 + 1;
                    v4[v3] = ((byte) (v8 >> 4));

                    this.m_state = v7;
                    this.m_op = v2;
                    return true;
                }
                case 3: {
                    v2 = v3 + 1;
                    v4[v3] = ((byte) (v8 >> 10));
                    v4[v2] = ((byte) (v8 >> 2));
                    ++v2;

                    this.m_state = v7;
                    this.m_op = v2;
                    return true;
                }
                case 4: {
                    this.m_state = 6;
                    return false;
                }
                default:
                    v2 = v3;

                    this.m_state = v7;
                    this.m_op = v2;
                    return true;
            }
        }
    }
}

