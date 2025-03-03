
<template>
  <div >
    <input type="checkbox"
           :id="id"
           :checked="isDone"
            @change="handleCheckboxChange"
            @click="addd"/>
    <label :for="id" >
<!--      {{id}}{{isDone}};;;;{{jd}}-->
      {{label}}
    </label>
  </div>
</template>

<script setup>
import { defineProps, ref, watch, watchEffect ,defineEmits} from 'vue'
import { nanoid } from 'nanoid'

const props = defineProps({
  label: {
    type: String,
    required: true
  },
  done: {
    type: Boolean,
    default: false
  }
})

// 状态管理修正
const isDone = ref(props.done)
const emit = defineEmits(['update:done']) // 新增事件发射



// 正确监听props变化
watch(
    () => props.done,
    (newVal) => {
      isDone.value  = newVal
    }
)
const jd=ref(0)
const addd=()=>{
  jd.value+=1;
}
// 双向绑定修正
const handleCheckboxChange = (e) => {

  isDone.value  = e.target.checked
  emit('update-done', isDone.value)  // 同步父组件状态
}

// 动态ID生成修正
const id = ref('')
watchEffect(() => {
  void jd.value//重要,检测到才会动态修改
  id.value  = `todo-${nanoid(8)}`
})



</script>



<style scoped>
.custom-checkbox > .checkbox-label {
  font-family: Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-weight: 400;
  font-size: 16px;
  font-size: 1rem;
  line-height: 1.25;
  color: #0b0c0c;
  display: block;
  margin-bottom: 5px;
}
.custom-checkbox > .checkbox {
  font-family: Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-weight: 400;
  font-size: 16px;
  font-size: 1rem;
  line-height: 1.25;
  box-sizing: border-box;
  width: 100%;
  height: 40px;
  height: 2.5rem;
  margin-top: 0;
  padding: 5px;
  border: 2px solid #0b0c0c;
  border-radius: 0;
  appearance: none;
}
.custom-checkbox > input:focus {
  outline: 3px dashed #fd0;
  outline-offset: 0;
  box-shadow: inset 0 0 0 2px;
}
.custom-checkbox {
  font-family: Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  font-weight: 400;
  font-size: 1.6rem;
  line-height: 1.25;
  display: block;
  position: relative;
  min-height: 40px;
  margin-bottom: 10px;
  padding-left: 40px;
  clear: left;
}
.custom-checkbox > input[type="checkbox"] {
  -webkit-font-smoothing: antialiased;
  cursor: pointer;
  position: absolute;
  z-index: 1;
  top: -2px;
  left: -2px;
  width: 44px;
  height: 44px;
  margin: 0;
  opacity: 0;
}
.custom-checkbox > .checkbox-label {
  font-size: inherit;
  font-family: inherit;
  line-height: inherit;
  display: inline-block;
  margin-bottom: 0;
  padding: 8px 15px 5px;
  cursor: pointer;
  touch-action: manipulation;
}
.custom-checkbox > label::before {
  content: "";
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  width: 40px;
  height: 40px;
  border: 2px solid currentcolor;
  background: transparent;
}
.custom-checkbox > input[type="checkbox"]:focus + label::before {
  border-width: 4px;
  outline: 3px dashed #228bec;
}
.custom-checkbox > label::after {
  box-sizing: content-box;
  content: "";
  position: absolute;
  top: 11px;
  left: 9px;
  width: 18px;
  height: 7px;
  transform: rotate(-45deg);
  border: solid;
  border-width: 0 0 5px 5px;
  border-top-color: transparent;
  opacity: 0;
  background: transparent;
}
.custom-checkbox > input[type="checkbox"]:checked + label::after {
  opacity: 1;
}
@media only screen and (min-width: 40rem) {
  label,
  input,
  .custom-checkbox {
    font-size: 19px;
    font-size: 1.9rem;
    line-height: 1.31579;
  }
}

</style>



<!--
<script setup>
import { ref, watchEffect } from 'vue'
import { nanoid } from 'nanoid'

// 实验控制参数
const dynamicPrefix = ref('todo') // 动态前缀输入
const renderCount = ref(0) // 渲染计数器

// 静态生成策略
const staticId = ref(nanoid(8))

// 动态生成策略
const dynamicId = ref('')
watchEffect(() => {
  dynamicId.value  = `${dynamicPrefix.value}-${nanoid(6)}`
})

// 强制重渲染方法
const forceRerender = () => {
  renderCount.value  += 1
}
</script>

<template>
  <div class="experiment-container">
    &lt;!&ndash; 控制面板 &ndash;&gt;
    <div class="control-panel">
      <label>动态前缀：
        <input v-model="dynamicPrefix" placeholder='输入前缀...'>
      </label>
      <button @click="forceRerender">强制重渲染（已渲染{{ renderCount }}次）</button>
    </div>

    &lt;!&ndash; 结果显示 &ndash;&gt;
    <div class="result-box static">
      <h3>静态ID策略</h3>
      <p>ID值：{{ staticId }}</p>
      <p class="tip">（初始化后不再变化）</p>
    </div>

    <div class="result-box dynamic">
      <h3>动态ID策略</h3>
      <p>ID值：{{ dynamicId }}</p>
      <p class="tip">（前缀或依赖变化时自动更新）</p>
    </div>
  </div>
</template>

<style scoped>
.experiment-container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
}

.control-panel {
  margin-bottom: 2rem;
  padding: 15px;
  background: #f5f5f5;
  border-radius: 6px;
}

input {
  padding: 8px;
  margin: 0 10px;
  border: 1px solid #ccc;
}

button {
  padding: 8px 15px;
  background: #2196f3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.result-box {
  padding: 15px;
  margin: 1rem 0;
  border: 2px dashed;
}

.static {
  border-color: #ff5722;
}

.dynamic {
  border-color: #4caf50;
}

.tip {
  color: #666;
  font-size: 0.9em;
}
</style>-->
