<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="书号">
        <el-input v-model="form.isbn" />
      </el-form-item>
      <el-form-item label="书名">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="form.author" />
      </el-form-item>
      <el-form-item label="出版社">
        <el-input v-model="form.publisher" />
      </el-form-item>
      <el-form-item label="出版时间">
        <el-col :span="11">
          <el-date-picker v-model="form.publish_date" type="date" placeholder="请输入出版日期" style="width: 100%;" />
        </el-col>
      </el-form-item>
      <el-form-item label="图书简介">
        <el-input v-model="form.description" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { saveBook,updateBook} from "@/api/book";

export default {
  data() {
    return {
      form: {
        isbn: '',
        title: '',
        author: '',
        publisher: '',
        publish_date: '',
        description: ''
      },
      mode: 'add' // 默认是新增模式
    }
  },
  created() {
    // 从查询参数中获取数据并初始化表单
    const query = this.$route.query;
    if (query) {
      this.form.isbn = query.isbn;
      this.form.title = query.title;
      this.form.author = query.author;
      this.form.publisher = query.publisher;
      this.form.publish_date = query.publish_date;
      this.form.description = query.description;
      this.mode = query.mode || 'add'; // 获取模式，默认是新增模式
    }
  },
  methods: {
    onSubmit() {
      if (this.mode === 'edit') {
        updateBook(this.form).then(response => {
          this.$notify.success({
            title: '成功',
            message: '更新成功！'
          })
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.message
          })
        })
      } else {
        saveBook(this.form).then(response => {
        this.$notify.success({
          title: '成功',
          message: '提交成功！'
        })
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.message
        })
      })
      }
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

