<script setup>
import {ref,defineEmits} from "vue";
const label=ref('')
const emit=defineEmits(['todo-added'])
const submit=()=>{
  if(label.value.length===0){
    return;
  }
  emit('todo-added',label)
}


</script>

<template>
  <form @submit.prevent="submit">
    <h2 class="label-wrapper">
      <label for="new-todo-input" class="label__lg">
        What needs to be done?
      </label>
    </h2>
    <input
        type="text"
        id="new-todo-input"
        name="new-todo"
        autocomplete="off"
        v-model="label"
        v-model.lazy.trim="label"
        class="input__lg"/>
    <button type="submit" class="btn btn__primary btn__lg">Add</button>
  </form>
</template>

<style scoped>

</style>