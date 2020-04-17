<template>
  <div id='main'>
    <br>
        <label>Image: </label>
        <input type="file" 
        v-on:change='setFile' 
        accept="image/*"><br>
        <img :src="imageUrl" height="150px">
        <button>Add Image</button>
</div>
</template>

<script>
//import authLib from '@/auth';
export default {

    data() {
        return { 
            image: null,
            imageUrl: '',
            binaryImage:''
        }
    },
    methods: {
        setFile(event) {
            const files = event.target.files
            this.image = event.target.files[0]
            let filename = files[0].name
            if (filename.lastIndexOf('.') <= 0) {
                return alert ('Add a valid image')
            }
            const fileReader = new FileReader()
            fileReader.addEventListener('load', () => {
                this.imageUrl = fileReader.result
            })
            fileReader.readAsDataURL(files[0])
        },
        createImage() {
            if (!this.image) {
                return alert ("Add Image")
            }
            // const newData = {
            //     image: this.image.toBinary()
        // }
    },
    created() {
    }
}
}
</script> 

<style>
    #input-section {
        background-color: lightgray;
        margin: 20px;
        padding: 20px;
        width: 20%;
    }
</style>