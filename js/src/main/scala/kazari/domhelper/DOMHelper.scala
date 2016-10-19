package kazari.domhelper

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.Div
import org.querki.jquery._

trait DOMHelper {
  val codeExcludeClass = "code-exclude"
  val codeSnippetsSelector = s"code.language-scala:not(.$codeExcludeClass)"
  val dependenciesMetaName = "evaluator-dependencies"
  val resolversMetaName = "evaluator-resolvers"
  val codeModalClass = "modal-fade-screen"
  val codeModalCloseButtonClass = "modal-close"
  val codeModalInternalTextArea = "modal-text-area"
  val codeModalButtonContainer = "modalButton"
  val codeModalEditorMaxHeightPercent = 80.0
  val decoratorButtonRunClass = "kazari-decorator-run"
  val decoratorButtonEditClass = "kazari-decorator-edit"
  val decoratorButtonSaveGistClass = "kazari-decorator-gist"
  val decoratorButtonPlayClass = "fa-play-circle"
  val decoratorButtonSpinnerClass = "fa-spinner fa-spin"
  val decoratorButtonDisableClass = "compiling"
  val kazariUrl = "https://github.com/47deg/sbt-microsites"

  def appendButton[T](targetNode: dom.Node,
      title: String,
      onClickFunction: Function[_, T],
      id: Option[String] = None): dom.Node = {

    val btnNode = document.createElement("button")
    btnNode.appendChild(document.createTextNode(title))
    btnNode.setAttribute("type", "button")
    btnNode.setAttribute("id", id.getOrElse(""))
    btnNode.addEventListener("click", onClickFunction)
    targetNode.appendChild(btnNode)
  }

  def createModalDiv(cssClass: String): Div = {
    import scalatags.JsDom.all._

    div(
      `class` := "modal",
      label(
        `for` := "modal-1",
        div(
          `class` := "modal-trigger",
          "Modal example"
        )
      ),
      input(
        `id` := "modal-1",
        `class` := "modal-state",
        `type` := "checkbox"
      ),
      div(
        `class` := "modal-fade-screen",
        div(
          `class` := "modal-inner",
          div(
            `class` := "modal-close",
            `for` := "modal-1",
            i(
              `class` := "fa fa-close"
            )
          ),
          div(
            `class` := "modal-content",
            textarea(
              `id` := codeModalInternalTextArea
            ),
            div(
              `class` := "compiler",
              ul(
                li(
                  a(
                    `class` := decoratorButtonRunClass,
                    i(
                      `class` := "fa fa-play-circle"
                    ),
                    "Run"
                  )
                ),
                li(
                  a(
                    `class` := decoratorButtonSaveGistClass,
                    i(
                      `class` := "fa fa-github-alt"
                    ),
                    "Save as Gist"
                  )
                ),
                li(
                  a(
                    href := kazariUrl,
                    target := "_blank",
                    "Kazari"
                  )
                )
              )
            )
          )
        )
      )
    ).render
  }

  def createDecoration(index: Int): Div = {
    import scalatags.JsDom.all._

    div(
      `id` := s"snippet-$index",
      `class` := "compiler",
      ul(
        li(
          a(
            `class` := decoratorButtonRunClass,
            i(
              `class` := "fa fa-play-circle"
            ),
            "Run"
          )
        ),
        li(
          a(
            `class` := decoratorButtonEditClass,
            i(
              `class` := "fa fa-pencil"
            ),
            "Edit"
          )
        ),
        li(
          a(
            href := kazariUrl,
            target := "_blank",
            "Kazari"
          )
        )
      )
    ).render
  }

  def getMetaContent(metaTagName: String): String = {
    val metaTag = document.querySelector(s"meta[property=" + """"""" + s"$metaTagName" + """"""" + "]")
    if (metaTag != null) {
      metaTag.getAttribute("content")
    } else {
      ""
    }
  }

  def applyModalStyles() = {
    $("#modal-1").on("change", { (e: JQueryEventObject, a: Any) =>
      if ($("#modal-1").is(":checked")) {
        $("body").addClass("modal-open")
      } else {
        $("body").removeClass("modal-open")
      }
    })

    $(".modal-fade-screen, .modal-close").on("click", { (e: JQueryEventObject, a: Any) =>
      $(".modal-state:checked").prop("checked", false).change()
    })

    $(".modal-inner").on("click", { (e: JQueryEventObject, a: Any) =>
      e.stopPropagation()
    })
  }

  def addClickListenerToButton(selector: String, function: (dom.MouseEvent) => Any) = {
    val btn = Option(document.querySelector(selector))
    btn.foreach { b =>
      b.addEventListener("click", function)
    }
  }

  def changeButtonIcon(selector: String, currentClass: String, nextClass: String) = {
    val btnImg = Option(document.querySelector(selector))
    btnImg foreach { $(_).removeClass(currentClass).addClass(nextClass) }
  }

  def toggleButtonActiveState(selector: String, active: Boolean) = {
    val btn = Option(document.querySelector(selector))
    btn foreach { b =>
      val _ = if (active) {
        $(b).addClass(decoratorButtonDisableClass)
      } else {
        $(b).removeClass(decoratorButtonDisableClass)
      }
    }
  }
}