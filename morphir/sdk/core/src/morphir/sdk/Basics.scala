/*
Copyright 2020 Morgan Stanley

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package morphir.sdk

import morphir.sdk.String.String

object Basics {

  type Bool = Boolean

  val True: Bool = true
  val False: Bool = false

  def Bool(bool: Boolean): Bool = bool

  @inline def and(a: Bool, b: Bool): Bool = a && b
  @inline def not(value: Bool): Bool = !value
  @inline def or(a: Bool, b: Bool): Bool = a || b
  @inline def xor(a: Bool, b: Bool): Bool = a ^ b
  @inline def toString(value: Bool): String = value.toString

  @inline def equal[A](a: A, b: A): morphir.sdk.Basics.Bool =
    a == b

  @inline def notEqual[A](a: A, b: A): morphir.sdk.Basics.Bool =
    a != b

  def lessThan[A](a: A, b: A)(
      implicit ordering: Ordering[A]
  ): morphir.sdk.Basics.Bool =
    ordering.lt(a, b)

  def greaterThan[A](a: A, b: A)(
      implicit ordering: Ordering[A]
  ): morphir.sdk.Basics.Bool =
    ordering.gt(a, b)

  def lessThanOrEqual[A](a: A, b: A)(
      implicit ordering: Ordering[A]
  ): morphir.sdk.Basics.Bool =
    ordering.lteq(a, b)

  def greaterThanOrEqual[A](a: A, b: A)(
      implicit ordering: Ordering[A]
  ): morphir.sdk.Basics.Bool =
    ordering.gteq(a, b)

  def min[A](a: A, b: A)(implicit ordering: Ordering[A]): A =
    if (lessThan(a, b)) a else b

  def max[A](a: A, b: A)(implicit ordering: Ordering[A]): A =
    if (greaterThan(a, b)) a else b

  def add[A: Numeric](left: A, right: A)(implicit numeric: Numeric[A]): A =
    numeric.plus(left, right)

  def subtract[A](left: A, right: A)(implicit numeric: Numeric[A]): A =
    numeric.minus(left, right)

  def multiply[A](left: A, right: A)(implicit numeric: Numeric[A]): A =
    numeric.times(left, right)

  def abs[A](value: A)(implicit numeric: Numeric[A]): A =
    numeric.abs(value)

  def negate[A](value: A)(implicit numeric: Numeric[A]): A =
    numeric.negate(value)

  type Int = scala.BigInt

  def Int(int: scala.Int): Int = int

  @inline def integerDivide(dividend: Int, divisor: Int): Int =
    dividend / divisor

  @inline def modBy(divisor: Int, dividend: Int): Int = (dividend % divisor).abs

  @inline def remainderBy(divisor: Int, dividend: Int): Int = dividend % divisor

  type Float = scala.Double

  def Float(double: scala.Double): Float = double

  @inline def divide(dividend: Float, divisor: Float): Float =
    dividend / divisor

  def identity[A](a: A): A = Predef.identity(a)

  def always[A, B](a: A): B => A = _ => a
}
