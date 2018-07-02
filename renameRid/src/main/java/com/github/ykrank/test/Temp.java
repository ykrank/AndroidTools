package com.github.ykrank.test;

public class Temp {
    private int[] m_alphabet;
    public int m_op;
    public byte[] m_output;
    private int m_state;
    private int m_value;

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

//                    label_66:
                    v5 = v6;
                }

                v3 = v2;
            }
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
