const app = Vue.
createApp({
    data() {
      return {
        mail1:"",
        contrasena1:"",
        nombre2:"",
        apellido2:"",
        mail2:"",
        contrasena2:"",
      }
    },
    created(){
      console.log("dddddddd");
    },
    methods: {
      postRegistro(){
        axios.post('/api/clients',`nombre=${this.nombre2}&apellido=${this.apellido2}&mail=${this.mail2}&password=${this.contrasena2}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
        .then(response => {
          console.log('registered')
          //funcion iniciar sesion
          axios.post('/api/login', `mail=${this.mail2}&password=${this.contrasena2}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(response => {
            console.log('signed in!!!');
            window.location.href="/Web/accounts.html"
            //funcion de crear cuenta
            axios.post('/api/clients/current/accounts',`accountType=corriente`)
              .then(response=>console.log('cuenta creada'))
              .catch(function (error) {
                if (error.response) {
                  console.log(error.response.data);
                  swal({
                    title: "error",
                    text: error.response.data, 
                    icon: "error",
                })
                }}
              );
          })
          .catch(function (error) {
            if (error.response) {
              console.log(error.response.data);
              swal({
                title: "error",
                text: error.response.data, 
                icon: "error",
            })
            }}
          );
        })
        .catch(function (error) {
          if (error.response) {
            console.log(error.response.data);
            swal({
              title: "error",
              text: error.response.data, 
              icon: "error",
          })
          }}
        );
      },
      postInicio(){
        axios.post('/api/login', `mail=${this.mail1}&password=${this.contrasena1}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
        .then(response => {
          console.log('signed in!!!');
          window.location.href="/Web/accounts.html"})
        /*.catch(function (error) {
          if (error.response) {
            console.log(error.response.data);
            swal({
              title: "error",
              text: error.response.data, 
              icon: "error",
            })
          }}
        ); */
        .catch(setTimeout(function(){
            swal({
              title: "error",
              text: "Verifique los perametros ingresados o si es nuevo registrese", 
              icon: "error",
            })
        },2000)
          
        )
      },
      postCerrar(){
        axios.post('/api/logout').then(response => console.log('signed out!!!'))
      },
    },
    computed: {

    },
  }).mount('#app')