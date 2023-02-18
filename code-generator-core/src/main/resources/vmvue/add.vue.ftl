<template>
  <div class="container">
    <a-form layout="vertical" :model="formData">
      <a-space direction="vertical" :size="16">
        <a-card class="general-card">
          <template #title>
            添加${tableDesc}
          </template>
          <a-row :gutter="80">

<#list  fields as field>
            <a-col :span="8">
              <a-form-item
                  label="${field.fieldDesc}"
                  field="${field.fieldName}"
              >
                <a-input
                    v-model="formData.${field.fieldName}"
                    placeholder="请输入${field.fieldDesc}"
                >
                </a-input>
              </a-form-item>
            </a-col>
</#list>

          </a-row>

        </a-card>
      </a-space>
      <div class="actions">
        <a-space>
          <a-button>
            重置
          </a-button>
          <a-button type="primary" :loading="loading" @click="onSubmitClick">
            提交
          </a-button>
        </a-space>
      </div>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import useLoading from '@/hooks/loading';
import {
    ${entityName}Create,
    ${entityName}Update,
    ${entityName}ById,
    ${entityName}Page,
    ${entityName}Delete,
    ${entityName}Deletes,
    ${entityName}QueryRequest,
    ${entityName}CreateRequest
    } from "./${tableName}Api";

const create:${entityName}CreateRequest={
<#list  fields as field>
  ${field.fieldName}: '',
</#list>
}
const formData = ref(create);

const { loading, setLoading } = useLoading();
const onSubmitClick = async () => {
  console.log(formData.value)
  ${entityName}Create(formData.value).then(
      (res)=>{
        if (res.code == 20000) {
            Message.success("创建成功")
        }

      }
  )

};
</script>

<script lang="ts">
export default {
  name: '${entityName}Add',
};
</script>

<style scoped lang="less">
.container {
  padding: 0 20px 40px 20px;
  overflow: hidden;
}

.actions {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  height: 60px;
  padding: 14px 20px 14px 0;
  background: var(--color-bg-2);
  text-align: right;
}
</style>
