const { useState } = React
const { ChangeEvent } = React

const root = ReactDOM.createRoot(document.getElementById('root'));

class PersonList extends React.Component {
  state = {
    persons: []
  }

  componentDidMount() {
    axios.get("/hrapi/persons")
      .then(res => {
        const persons = res.data;
        this.setState({ persons });
      })
  }

  deleteRow(id, e) {
    axios.delete(`/hrapi/persons/${id}`)
      .then(res => {
        console.log(res);
        console.log(res.data);
        window.location.reload(false);
      })
  }

  render() {
    let tb_data = this.state.persons.map((item) => {
      return (
        <tr key={item.id}>
          <td>{item.name}</td>
          <td>{item.email}</td>
          <td>{item.phone}</td>
          <td><button className="btn btn-danger bg-gradient" onClick={(e) => this.deleteRow(item.id, e)}>Delete</button>
          </td>
        </tr>
      )
    })

    return (
      <div className="container p-3 rounded border-primary bg-gradient border-3">
        <table className="table  table-bordered border-primary bg-gradient table-hover">
          <thead className="table-primary border-primary bg-gradient">
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Phone number</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {tb_data}
          </tbody>
        </table>
      </div>
    )
  }
}

class PersonAdd extends React.Component {
  state = {
    name: '',
    email: '',
    phone: ''
  }

  handleChangeName = event => {
    this.setState({ name: event.target.value });
  }

  handleChangeEmail = event => {
    this.setState({ email: event.target.value });
  }

  handleChangePhone = event => {
    this.setState({ phone: event.target.value });
  }

  handleSubmit = event => {
    event.preventDefault();

    axios.post("/hrapi/persons", {
      name: this.state.name,
      email: this.state.email,
      phone: this.state.phone
    })
      .then(res => {
        console.log(res);
        console.log(res.data);
        window.location.reload(false);
      })
  }

  render() {
    return (
      <div className="container p-3 rounded border border-3">
        <form className="form-group" onSubmit={this.handleSubmit}>
          <label>
            Name:
            <input type="text" className="form-control" name="name" onChange={this.handleChangeName} />
          </label>
          <label>
            Email:
            <input type="text" className="form-control" name="email" onChange={this.handleChangeEmail} />
          </label>
          <label>
            Phone:
            <input type="text" className="form-control" name="phone" onChange={this.handleChangePhone} />
          </label>
          <button type="submit" className="mt-3 form-control btn btn-primary bg-gradient">Add</button>
        </form>
      </div>
    )
  }
}

function UploadFile() {
  const [file, setFile] = useState();
  const UPLOAD_ENDPOINT = "/upload";
  const handleSubmit = async e => {
    e.preventDefault();
    let res = await uploadFile(file);
    console.log(res.data);
  };
  const uploadFile = async file => {
    const formData = new FormData();
    formData.append("file", file);

    return await axios.post(UPLOAD_ENDPOINT, formData, {
      headers: {
        "content-type": "multipart/form-data; charset=UTF-8"
      }
    })
      .then(res => {
        console.log(res);
        window.location.reload(false);
      });
  };
  const handleOnChange = e => {
    console.log(e.target.files[0]);
    setFile(e.target.files[0]);
  };
  return (
    <div className="container w-25 p-3 rounded border border-3" >
      <form form-control onSubmit={handleSubmit}>
        <input type="file" accept=".csv" className="form-control" onChange={handleOnChange} />
        <button type="submit" className="form-control btn btn-primary bg-gradient">Upload File</button>
      </form>
    </div>
  );
}

function App() {
  return (
    <div >
      <div className="row">
        <div className="col-1"></div>
        <div className="col-10">
          <h1 className="container rounded border border-3 bg-success bg-gradient bg-success p-3 mt-3 text-center">CSV Uploader</h1>
        </div>
        <div className="col-1"></div>
      </div>
      <UploadFile />
      <div className="row">
        <div className="col-1"></div>
        <div className="col-8">
          <PersonList />
        </div>
        <div className="col-2">  
          <PersonAdd />
        </div>
        <div className="col-1"></div>
      </div>
    </div >
  );
}

root.render(<App />);
