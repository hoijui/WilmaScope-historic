/* Generated By:JavaCC: Do not edit this line. DotParserTokenManager.java */
package org.wilmascope.dotparser;
import java.util.Vector;
import java.io.*;
import java.awt.Point;

public class DotParserTokenManager implements DotParserConstants
{
  public static  java.io.PrintStream debugStream = System.out;
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x180003ffe00L) != 0L)
         {
            jjmatchedKind = 22;
            return 1;
         }
         if ((active0 & 0x2000000000L) != 0L)
            return 3;
         return -1;
      case 1:
         if ((active0 & 0x3f7e00L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 1;
            return 1;
         }
         if ((active0 & 0x8000L) != 0L)
            return 1;
         return -1;
      case 2:
         if ((active0 & 0x3f5e00L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 2;
            return 1;
         }
         if ((active0 & 0x2000L) != 0L)
            return 1;
         return -1;
      case 3:
         if ((active0 & 0x800L) != 0L)
            return 1;
         if ((active0 & 0x3f5600L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 3;
            return 1;
         }
         return -1;
      case 4:
         if ((active0 & 0x115200L) != 0L)
            return 1;
         if ((active0 & 0x2e0400L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 4;
            return 1;
         }
         return -1;
      case 5:
         if ((active0 & 0x260000L) != 0L)
            return 1;
         if ((active0 & 0x80400L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 5;
            return 1;
         }
         return -1;
      case 6:
         if ((active0 & 0x400L) != 0L)
            return 1;
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 6;
            return 1;
         }
         return -1;
      case 7:
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 7;
            return 1;
         }
         return -1;
      case 8:
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 8;
            return 1;
         }
         return -1;
      case 9:
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 9;
            return 1;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 34:
         return jjStopAtPos(0, 34);
      case 44:
         return jjStopAtPos(0, 36);
      case 45:
         return jjMoveStringLiteralDfa1_0(0x4000000000L);
      case 46:
         return jjStartNfaWithStates_0(0, 37, 3);
      case 59:
         return jjStopAtPos(0, 30);
      case 61:
         jjmatchedKind = 33;
         return jjMoveStringLiteralDfa1_0(0x100000000L);
      case 91:
         return jjStopAtPos(0, 31);
      case 93:
         return jjStopAtPos(0, 35);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 100:
         return jjMoveStringLiteralDfa1_0(0x400L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x8000000000L);
      case 103:
         return jjMoveStringLiteralDfa1_0(0x200L);
      case 104:
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x101000L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x800L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x10000004000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x30000L);
      case 123:
         return jjStopAtPos(0, 28);
      case 125:
         return jjStopAtPos(0, 29);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 44:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStopAtPos(1, 39);
         else if ((active0 & 0x10000000000L) != 0L)
            return jjStopAtPos(1, 40);
         break;
      case 62:
         if ((active0 & 0x4000000000L) != 0L)
            return jjStopAtPos(1, 38);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x101000L);
      case 98:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(1, 15, 1);
         break;
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x60000L);
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x210400L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x82800L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x200L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x4200L);
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x10800L);
      case 103:
         return jjMoveStringLiteralDfa3_0(active0, 0x400L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x60000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x280000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 115:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(2, 13, 1);
         break;
      case 121:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000L);
      case 101:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(3, 11, 1);
         return jjMoveStringLiteralDfa4_0(active0, 0x101000L);
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x60000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000L);
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x4200L);
      case 114:
         return jjMoveStringLiteralDfa4_0(active0, 0x400L);
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x100000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x400L);
      case 101:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(4, 14, 1);
         else if ((active0 & 0x100000000L) != 0L)
            return jjStopAtPos(4, 32);
         return jjMoveStringLiteralDfa5_0(active0, 0x280000L);
      case 104:
         if ((active0 & 0x200L) != 0L)
            return jjStartNfaWithStates_0(4, 9, 1);
         else if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(4, 16, 1);
         return jjMoveStringLiteralDfa5_0(active0, 0x60000L);
      case 108:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(4, 12, 1);
         break;
      case 114:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(4, 20, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 110:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(5, 21, 1);
         return jjMoveStringLiteralDfa6_0(active0, 0x80000L);
      case 112:
         return jjMoveStringLiteralDfa6_0(active0, 0x400L);
      case 116:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(5, 17, 1);
         else if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(5, 18, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 104:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(6, 10, 1);
         break;
      case 116:
         return jjMoveStringLiteralDfa7_0(active0, 0x80000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x80000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa9_0(active0, 0x80000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa10_0(active0, 0x80000L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
static private final int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(10, 19, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
static private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
static private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
static private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 28;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 23)
                        kind = 23;
                     jjCheckNAddStates(0, 2);
                  }
                  else if (curChar == 47)
                     jjAddStates(3, 5);
                  else if (curChar == 46)
                     jjCheckNAdd(3);
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 2:
                  if (curChar == 46)
                     jjCheckNAdd(3);
                  break;
               case 3:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(3);
                  break;
               case 4:
                  if (curChar == 47)
                     jjAddStates(3, 5);
                  break;
               case 5:
                  if (curChar == 47)
                     jjCheckNAddStates(6, 8);
                  break;
               case 6:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(6, 8);
                  break;
               case 7:
                  if ((0x2400L & l) != 0L && kind > 6)
                     kind = 6;
                  break;
               case 8:
                  if (curChar == 10 && kind > 6)
                     kind = 6;
                  break;
               case 9:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 11:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 12:
                  if (curChar == 42)
                     jjCheckNAddStates(9, 11);
                  break;
               case 13:
                  if ((0xffff7bffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(14, 12);
                  break;
               case 14:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(14, 12);
                  break;
               case 15:
                  if (curChar == 47 && kind > 7)
                     kind = 7;
                  break;
               case 16:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 17:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(18, 19);
                  break;
               case 18:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(18, 19);
                  break;
               case 19:
                  if (curChar == 42)
                     jjCheckNAddStates(12, 14);
                  break;
               case 20:
                  if ((0xffff7bffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(21, 19);
                  break;
               case 21:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(21, 19);
                  break;
               case 22:
                  if (curChar == 47 && kind > 8)
                     kind = 8;
                  break;
               case 23:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjCheckNAddStates(0, 2);
                  break;
               case 24:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjCheckNAdd(24);
                  break;
               case 25:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(25, 26);
                  break;
               case 26:
                  if (curChar == 46)
                     jjCheckNAdd(27);
                  break;
               case 27:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(27);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
               case 1:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjCheckNAdd(1);
                  break;
               case 6:
                  jjAddStates(6, 8);
                  break;
               case 11:
                  jjCheckNAddTwoStates(11, 12);
                  break;
               case 13:
               case 14:
                  jjCheckNAddTwoStates(14, 12);
                  break;
               case 18:
                  jjCheckNAddTwoStates(18, 19);
                  break;
               case 20:
               case 21:
                  jjCheckNAddTwoStates(21, 19);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 6:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(6, 8);
                  break;
               case 11:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 13:
               case 14:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(14, 12);
                  break;
               case 18:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(18, 19);
                  break;
               case 20:
               case 21:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(21, 19);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 28 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private static final int jjStopStringLiteralDfa_1(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private static final int jjStartNfa_1(int pos, long active0)
{
   return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
}
static private final int jjStartNfaWithStates_1(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_1(state, pos + 1);
}
static private final int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      default :
         return jjMoveNfa_1(0, 0);
   }
}
static private final int jjMoveNfa_1(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 3;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  jjAddStates(15, 16);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (curChar == 91)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 1:
                  if ((0xffffffffdfffffffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if (curChar == 93)
                     kind = 27;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(15, 16);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   24, 25, 26, 5, 16, 17, 6, 7, 9, 12, 13, 15, 19, 20, 22, 1, 
   2, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, "\147\162\141\160\150", 
"\144\151\147\162\141\160\150", "\156\157\144\145", "\154\141\142\145\154", "\160\157\163", 
"\163\150\141\160\145", "\142\142", "\167\151\144\164\150", "\167\145\151\147\150\164", 
"\150\145\151\147\150\164", "\143\157\156\143\145\156\164\162\141\164\145", "\154\141\171\145\162", 
"\155\151\156\154\145\156", null, null, null, null, null, null, "\173", "\175", "\73", "\133", 
"\75\164\162\165\145", "\75", "\42", "\135", "\54", "\56", "\55\76", "\145\54", "\163\54", };
public static final String[] lexStateNames = {
   "DEFAULT", 
   "NODEPARAMETERS", 
};
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x1fff1fffe01L, 
};
static final long[] jjtoSkip = {
   0xe0001feL, 
};
static final long[] jjtoSpecial = {
   0x1c0L, 
};
static private SimpleCharStream input_stream;
static private final int[] jjrounds = new int[28];
static private final int[] jjstateSet = new int[56];
static protected char curChar;
public DotParserTokenManager(SimpleCharStream stream)
{
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}
public DotParserTokenManager(SimpleCharStream stream, int lexState)
{
   this(stream);
   SwitchTo(lexState);
}
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 28; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
static public void SwitchTo(int lexState)
{
   if (lexState >= 2 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static private final Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

public static final Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }

   switch(curLexState)
   {
     case 0:
       try { input_stream.backup(0);
          while ((curChar < 64 && (0x100002600L & (1L << curChar)) != 0L) || 
                 (curChar >> 6) == 1 && (0x10000000L & (1L << (curChar & 077))) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       try { input_stream.backup(0);
          while (curChar <= 32 && (0x100000200L & (1L << curChar)) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
           }
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

}
