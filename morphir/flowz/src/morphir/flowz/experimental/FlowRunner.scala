package morphir.flowz.experimental

import zio.internal.Platform
import zio.{ ExecutionStrategy, Exit, FiberFailure, Has, Layer, Managed, Runtime, URIO }

final case class FlowRunner[+InitialState, Trg, R <: Has[_], E](
  executor: FlowExecutor[InitialState, Trg, R, E],
  platform: Platform = Platform.makeDefault().withReportFailure(_ => ()),
  reporter: FlowReporter[E] = FlowReporter.silent, //TODO: Make this a default one that actually does something
  bootstrap: Layer[Nothing, FlowBaseEnv] = FlowBaseEnv.default
) { self =>
  lazy val runtime: Runtime[Unit] = Runtime((), platform)

  /**
   * Runs the flow, producing the execution results.
   */
  def run(flow: ExecutableFlow[InitialState, Trg, R, E]): URIO[FlowBaseEnv, ExecutedFlow[E]] =
    executor.run(flow, ExecutionStrategy.ParallelN(4)).timed.flatMap { case (duration, results) =>
      reporter(duration, results).as(results)
    }

  /**
   * An unsafe, synchronous run of the specified flow.
   */
  def unsafeRun(flow: ExecutableFlow[InitialState, Trg, R, E]): ExecutedFlow[E] =
    self.runtime.unsafeRun(run(flow).provideLayer(bootstrap))

  /**
   * An unsafe, asynchronous run of the specified flow.
   */
  def unsafeRunAsync(flow: ExecutableFlow[InitialState, Trg, R, E])(k: ExecutedFlow[E] => Unit): Unit =
    runtime.unsafeRunAsync(run(flow).provideLayer(bootstrap)) {
      case Exit.Success(v) => k(v)
      case Exit.Failure(c) => throw FiberFailure(c)
    }

  /**
   * An unsafe, synchronous run of the specified flow.
   */
  def unsafeRunSync(
    flow: ExecutableFlow[InitialState, Trg, R, E]
  ): Exit[Nothing, ExecutedFlow[E]] =
    self.runtime.unsafeRunSync(run(flow).provideLayer(bootstrap))

  def withPlatform(f: Platform => Platform): FlowRunner[InitialState, Trg, R, E] =
    copy(platform = f(platform))

  private[flowz] def buildRuntime: Managed[Nothing, Runtime[FlowBaseEnv]] =
    bootstrap.toRuntime(platform)
}
