/*
 * Copyright 2019 BusyMachines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package busymachines.pureharm.effects

trait PureharmIOApp extends IOApp {

  /** We want to ensure that this is evaluated only once, otherwise we introduce too many possibilities of shooting
    * oneself in the foot.
    *
    * {{{
    * //created by mixing in the pureharm goodies
    * import myapp.effects._
    * //or, in lieu of that:
    * //import busymachines.pureharm.effects._
    *
    * object MyApp extends PureharmIOApp {
    *
    *   override ioRuntime: Later[(ContextShift[IO], Timer[IO])] =
    *     IORuntime.defaultMainRuntime("my-app")
    *
    *   //the usual... time to start instantiating all goodies
    *   def run(args: List[String]): IO[ExitCode] = IO.never[ExitCode]
    * }
    *
    * }}}
    *
    * @return
    */
  val ioRuntime: Later[(ContextShift[IO], Timer[IO])]

  /** */
  override protected def contextShift: ContextShift[IO] = ioRuntime.value._1

  /** Removing implicitness brought in by IOApp, to make it clearer what's going on.
    */
  override protected def timer: Timer[IO] = ioRuntime.value._2

}
