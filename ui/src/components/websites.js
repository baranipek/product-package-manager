import React from 'react';
import { Link } from 'react-router-dom';

class Websites extends React.Component {
	constructor(props) {
		super(props);
		this.state = {websites: []};
		this.headers = [
			{ key: 'id', label: 'Id'},
			{ key: 'name', label: 'Name' },
			{ key: 'description', label: 'Description' },
			{ key: 'totalPrice', label: 'Total Price'},
			{ key: 'exchangeRate', label: 'Rate' },
			{ key: 'products', label: 'Product Ids' }
		];
		this.deleteWebsite = this.deleteWebsite.bind(this);
	}
	
	componentDidMount() {
		fetch('http://localhost:8080/api/v1/packages')
			.then(response => {
				return response.json();
			}).then(result => {
				console.log(result);
				this.setState({
					websites:result
				});
			});
	}

	deleteWebsite(id) {
		if(window.confirm("Are you sure want to delete?")) {
			fetch('http://localhost:8080/api/v1/packages/' + id, {
				method: 'DELETE',
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			}).then(response => {
				if(response.status === 200) {
					alert("Website deleted successfully");
					fetch('http://localhost:8080/api/v1/packages')
						.then(response => {
							return response.json();
						}).then(result => {
						console.log(result);
						this.setState({
							websites:result
						});
					});
				}
			});
		}
	}

	render() {
		return (
			<div id="container">
				<Link to="/create">Add Package</Link>
				<p/>
				<table>
					<thead>
						<tr>
						{
							this.headers.map(function(h) {
								return (
									<th key = {h.key}>{h.label}</th>
								)
							})
						}
						  <th>Actions</th>
						</tr>
					</thead>
					<tbody>
						{
							this.state.websites.map(function(item, key) {
							return (
								<tr key = {key}>
								    <td>{item.id}</td>
								    <td>{item.name}</td>
								    <td>{item.description}</td>
									<td>{item.totalPrice}</td>
									<td>{item.exchangeRate}</td>
									<td>{item.products.map(product => <div><div>{product}</div>
										<div id="div_link">
											<p> <a href={`https://product-service.herokuapp.com/api/v1/products/${product}`}>
												See the product </a></p>
										</div>
									</div>)}
									</td>
								  <td>
										<Link to={`/update/${item.id}`}>Edit</Link>
										&nbsp;
									  <a href="javascript:void(0);" onClick={this.deleteWebsite.bind(this, item.id)}>Delete</a>
								  </td>
								</tr>
											)
							}.bind(this))
						}
					</tbody>
				</table>
			</div>
		)
	}
}
export default Websites;