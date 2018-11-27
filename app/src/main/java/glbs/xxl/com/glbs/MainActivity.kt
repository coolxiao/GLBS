package glbs.xxl.com.glbs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.execute
import kotlinx.android.synthetic.main.activity_main.input_number
import kotlinx.android.synthetic.main.activity_main.radio_group

class MainActivity : AppCompatActivity() {
  var a: Float? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    radio_group.setOnCheckedChangeListener { radioGroup, i ->
      when (radioGroup.id) {
        R.id.radio__button_1 -> a = 0.05f
        R.id.radio__button_2 -> a = 0.01f
      }
    }

    execute.setOnClickListener {
      val numberTxt = input_number.text.toString()
      val numbers = numberTxt.split(",").toList()
      if (numbers.isEmpty() || numbers.size < 3 || numbers.size > 50) {
        Toast.makeText(this, "请输入3到50个数", Toast.LENGTH_LONG).show()
        return@setOnClickListener
      }
      val list = arrayListOf<Float>()
      numbers.forEach {
        try {
          list.add(it.toFloat())
        } catch (e: Exception) {
          Toast.makeText(this, "请输入数字", Toast.LENGTH_LONG).show()
          return@setOnClickListener
        }
      }
      var total = 0f
      list.forEach {
        total += it
      }

      val average = total / numbers.size

      //差值平方集合
      var diff2total = 0f
      list.forEach {
        diff2total += (((it - average) * (it - average)))
      }

      var s = diff2total / (list.size - 1)
    }
  }
}
